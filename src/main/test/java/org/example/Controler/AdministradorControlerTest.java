package org.example.Controler;

import io.javalin.http.Context;
import org.example.Model.Administrador;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class AdministradorControlerTest {

    @Test
    public void testGetListar() {
        Context ctx = mock(Context.class);
        when(ctx.status(anyInt())).thenReturn(ctx);

        AdministradorControler controler = new AdministradorControler();
        controler.listar(ctx);

        verify(ctx, atLeastOnce()).json(any());
        verify(ctx, atLeastOnce()).status(200);
    }

    @Test
    public void testPostCadastrar() {
        Context ctx = mock(Context.class);

        Administrador admMock = new Administrador();
        admMock.setNome("Admin Teste");
        admMock.setEmail("teste@empresa.com");
        admMock.setCargo("Chefe");

        when(ctx.bodyAsClass(Administrador.class)).thenReturn(admMock);
        when(ctx.status(anyInt())).thenReturn(ctx);

        AdministradorControler controler = new AdministradorControler();
        controler.cadastrar(ctx);

        verify(ctx, atLeastOnce()).status(201);
    }
}