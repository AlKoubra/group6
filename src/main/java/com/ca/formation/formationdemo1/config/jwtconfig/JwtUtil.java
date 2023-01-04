package com.ca.formation.formationdemo1.config.jwtconfig;
import com.ca.formation.formationdemo1.models.Utilisateur;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    // mettre le jwtSecret= "Base-64"
    static String originalInput="TWV0dHJlIG1vbiB0b2tlbiBlbiBiYXNlIDY0IA==";
    private static final  String jwtSecret=  Base64.getEncoder().encodeToString(originalInput.getBytes());

    Logger logger= LoggerFactory.getLogger(  JwtUtil.class);

    // generer JWT

    public String generateAccesToken(Utilisateur utilisateur){
        Claims claims = Jwts.claims().setSubject(utilisateur.getUsername());
        claims.put("scopes", utilisateur.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(utilisateur.getName()+","+utilisateur.getUsername())
                .setIssuer("formation.ca")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 *1000))// 1 jour
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public String refreshAccesToken(Utilisateur utilisateur){
        return Jwts.builder()
                .setSubject(utilisateur.getName()+","+utilisateur.getUsername())
                .setIssuer("formation.ca")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 *1000))// 1 semaine
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    // Recuperer le username
     String getUsername(String token){
        Claims claims = getClaims(token);
       return claims.getSubject().split(",")[1];
    }

    // Recuperer les claims
    Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    // Recuperer la Date d'expiration
     Date getExpirationDate(String token){
       return getClaims(token).getExpiration();
    }


    // Verifier la validité du token
     boolean validate(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex){
            logger.info("Invalide Signature Jwt - "+ex.getMessage());
        } catch (ExpiredJwtException ex){
            logger.info("Expiration du Jwt - "+ex.getMessage());
        }catch (UnsupportedJwtException ex){
            logger.info("Token jwt non supporté - "+ex.getMessage());
        }catch (IllegalArgumentException ex){
            logger.info("Invalide claims Jwt - "+ex.getMessage());
        }catch (MalformedJwtException ex){
            logger.info("Token jwt mal formatter - "+ex.getMessage());
        }

        return false;
    }
}
