package app.auth.auth_jwt.config;

import app.auth.auth_jwt.account.annotations.AuthAction;
import app.auth.auth_jwt.account.entities.OtpToken;
import app.auth.auth_jwt.account.entities.User;
import app.auth.auth_jwt.account.repositories.OtpTokenRepository;
import app.auth.auth_jwt.account.repositories.PermissionRepository;
import app.auth.auth_jwt.account.repositories.RoleRepository;
import app.auth.auth_jwt.account.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    final EnvApp envApp;
    private final String bearer="Bearer ";
    private final String authorization="Authorization";
    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final PermissionRepository permissionRepository;
    final ResourceLoader resourceLoader;
    final OtpTokenRepository otpTokenRepository;
    public  Key getPrivateKey() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:"+this.envApp.getJwtPrivateKey());
      //  Resource resource = resourceLoader.getResource("classpath:ok.txt");
        byte[] privateKeyBytes = Files.readAllBytes(resource.getFile().toPath());
       // InputStream inputStream = resource.getInputStream();
       // byte[] privateKeyBytes = inputStream.readAllBytes();
      //  DefaultResourceLoader loader = new DefaultResourceLoader();
     //   Resource resource = loader.getResource("classpath:"+this.envApp.getJwtPrivateKey());
      //  byte[] privateKeyBytes = ResourceUtils.getFile("classpath:"+this.envApp.getJwtPrivateKey()).g().readAllBytes();
       // byte[] privateKeyBytes = Files.readAllBytes(Paths.get(this.envApp.getJwtPrivateKey()));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(spec);
        return Keys.hmacShaKeyFor(privateKey.getEncoded());
    }
    public  Key getPublicKey() throws Exception {
      //  byte[] publicKeyBytes = Files.readAllBytes(Paths.get(this.envApp.getJwtPublicKey()));
        Resource resource = resourceLoader.getResource("classpath:"+this.envApp.getJwtPublicKey());
        InputStream inputStream = resource.getInputStream();
        byte[] publicKeyBytes = inputStream.readAllBytes();
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);
        return Keys.hmacShaKeyFor(publicKey.getEncoded());
    }
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.getSecreteKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Map<String, Object> extraClaims,
                                UserDetails userDetails) throws Exception {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()  ))
                .setExpiration(new Date(System.currentTimeMillis() +this.getTTL() ))
                .signWith(this.getSigningKey(), SignatureAlgorithm.HS256) //SECRETE HS256
              //  .signWith(this.getPrivateKey(), SignatureAlgorithm.RS256) // PRIVATE KEY
                .compact();
    }
    public String generateToken( UserDetails userDetails)  {
        try {
            return generateToken(new HashMap<>(),userDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Claims getAllClaims(String token) throws Exception {
        return Jwts
                .parserBuilder()
                .setSigningKey(this.getSigningKey())  // SECRETE HS256
               // .setSigningKey(this.getPublicKey()) //// PUBLIC KEY
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getToken(HttpServletRequest request){
        String tokenHeader = request.getHeader(authorization);
        if(tokenHeader != null){
            return tokenHeader.substring(bearer.length());
        }
        return null;
    }
    public boolean isBearer(HttpServletRequest request){
        String tokenHeader = request.getHeader(authorization);
        if(tokenHeader != null){
            return tokenHeader.startsWith(bearer);
        }
        return false;
    }
    public boolean isTokenValid(String token,UserDetails userDetails){
        return this.getUserName(token).equals(userDetails.getUsername()) && !this.tokenIsExpired(token);
    }


    public OtpToken getTokenOtp(String email){
        var  otp =  OtpToken.builder()
                .otp(this.generateOtp(6))
                .token(this.srtRandom(70))
                .email(email)
                .build();
        otp =  this.otpTokenRepository.save(otp);

        return otp;
    }
    public  String generateOtp(int length) {
        Random rand = new Random();
        StringBuilder randomDigits = new StringBuilder();
        for (int i = 0; i < length; i++) {
            randomDigits.append(rand.nextInt(length));
        }
        return randomDigits.toString();
    }
    public  String generateRandomNumber(int length) {
        String timestamp = Long.toString(System.currentTimeMillis());
        Random rand = new Random();
        StringBuilder randomDigits = new StringBuilder();
        for (int i = 0; i < length; i++) {
            randomDigits.append(rand.nextInt(length));
        }
        return timestamp + randomDigits;
    }

    public  String srtRandom(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
            sb.append(ALPHA_NUMERIC_STRING.charAt(index));
        }
        return sb.toString();
    }


    private boolean tokenIsExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return getAllClaim(token,Claims::getExpiration);
    }


    public String getUserName(String token){
        return this.getAllClaim(token,Claims::getSubject);
    }
    public  <T> T  getAllClaim(String token, Function<Claims,T> claimsTFunction) {
        final Claims claims;
        try {
            claims = this.getAllClaims(token);
        } catch (Exception e) {
            return null;
        }
        return claimsTFunction.apply(claims);
    }


    private String getSecreteKey(){
        return  this.envApp.getJwtSecret();
    }
    public Integer getTTL(){
        return this.envApp.getJwtTtl() * 60 * 1000;
    }
    public Integer getTTLConfirmation(){
        return this.envApp.getJwtTtlConfirmation() * 60 * 1000;
    }

    public boolean can(AuthAction authAction, String username){
        boolean can= false;
        Optional<User> user = this.userRepository.findByUsername(username);
        try {
            if(user.isPresent()){
                can =  this.userRepository.hasPermission(user.get().getId(), authAction.abilityCode()) || user.get().isAdmin();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error to get role for dynamique profilage");
        }
        return can ;
    }
}
