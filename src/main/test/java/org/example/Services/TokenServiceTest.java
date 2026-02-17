package org.example.Services;

import org.example.Model.Administrador;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TokenServiceTest {

    @Test
    public void testGerarToken() {
        Administrador adminMock = new Administrador(1, "Admin Teste", "Gerente", "teste@empresa.com");
        String token = TokenService.gerarToken(adminMock);

        assertNotNull(token, "O token gerado não deveria ser nulo");

        String[] partesDoToken = token.split("\\.");
        assertEquals(3, partesDoToken.length, "O token JWT deve ter exatamente 3 partes");
    }

    @Test
    public void testValidarTokenComSucesso() {
        Administrador adminMock = new Administrador(2, "Admin Diretor", "Diretor", "diretor@empresa.com");
        String token = TokenService.gerarToken(adminMock);

        String emailValidado = TokenService.validarToken(token);

        assertEquals("diretor@empresa.com", emailValidado, "O email extraído deve ser igual ao do administrador");
    }

    @Test
    public void testValidarTokenInvalido() {
        String tokenFalso = "Bearer token.inventado.falso";
        String resultado = TokenService.validarToken(tokenFalso);

        assertNull(resultado, "Um token inválido deve retornar null");
    }
}