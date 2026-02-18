package org.example.Controler;

import io.javalin.http.Context;
import io.javalin.validation.Validator;
import org.example.Model.Funcionario;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class FuncionarioControlerTest {

    @Test
    public void testGetAllFuncionarios() {
        Context ctx = mock(Context.class);

        Validator<Integer> valMock = mock(Validator.class);
        when(valMock.getOrDefault(any())).thenReturn(1);
        when(ctx.queryParamAsClass(anyString(), eq(Integer.class))).thenReturn(valMock);
        when(ctx.status(anyInt())).thenReturn(ctx);

        FuncionarioControler controler = new FuncionarioControler();
        controler.getAll(ctx);

        verify(ctx, atLeastOnce()).status(200);
    }

    @Test
    public void testGetOneFuncionario() {
        Context ctx = mock(Context.class);
        when(ctx.pathParam("id")).thenReturn("1");
        when(ctx.status(anyInt())).thenReturn(ctx);

        FuncionarioControler controler = new FuncionarioControler();
        controler.getOne(ctx);

        verify(ctx, atLeastOnce()).pathParam("id");
    }

    @Test
    public void testPostCreateFuncionario() {
        Context ctx = mock(Context.class);
        Funcionario funcMock = new Funcionario();
        funcMock.setNome("Ruan Teste");
        funcMock.setCargo("Desenvolvedor");
        funcMock.setDepartamento(1);

        when(ctx.bodyAsClass(Funcionario.class)).thenReturn(funcMock);
        when(ctx.status(anyInt())).thenReturn(ctx);

        FuncionarioControler controler = new FuncionarioControler();
        controler.create(ctx);

        verify(ctx, atLeastOnce()).status(201);
    }

    @Test
    public void testPutUpdateFuncionario() {
        Context ctx = mock(Context.class);
        when(ctx.pathParam("id")).thenReturn("1");

        Funcionario funcMock = new Funcionario();
        funcMock.setNome("Nome Atualizado");

        when(ctx.bodyAsClass(Funcionario.class)).thenReturn(funcMock);
        when(ctx.status(anyInt())).thenReturn(ctx);

        FuncionarioControler controler = new FuncionarioControler();
        controler.update(ctx);

        verify(ctx, atLeastOnce()).pathParam("id");
    }

    @Test
    public void testDeleteFuncionario() {
        Context ctx = mock(Context.class);
        when(ctx.pathParam("id")).thenReturn("1");
        when(ctx.status(anyInt())).thenReturn(ctx);

        FuncionarioControler controler = new FuncionarioControler();
        controler.delete(ctx);

        verify(ctx, atLeastOnce()).pathParam("id");
    }
}