package org.example.Controler;

import io.javalin.http.Context;
import io.javalin.validation.Validator;
import org.example.Model.Departamento;
import org.example.Model.Funcionario;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class DepartamentoControlerTest {

    @Test
    public void testGetAllDepartamentos() {
        Context ctx = mock(Context.class);

        Validator<Integer> valMock = mock(Validator.class);
        when(valMock.getOrDefault(any())).thenReturn(1);
        when(ctx.queryParamAsClass(anyString(), eq(Integer.class))).thenReturn(valMock);
        when(ctx.status(anyInt())).thenReturn(ctx);

        DepartamentoControler controler = new DepartamentoControler();
        controler.getAll(ctx);

        verify(ctx, atLeastOnce()).status(200);
    }

    @Test
    public void testGetOneDepartamento() {
        Context ctx = mock(Context.class);
        when(ctx.pathParam("id")).thenReturn("1");
        when(ctx.status(anyInt())).thenReturn(ctx);

        DepartamentoControler controler = new DepartamentoControler();
        controler.getOne(ctx);

        verify(ctx, atLeastOnce()).pathParam("id");
    }

    @Test
    public void testPostCreateDepartamento() {
        Context ctx = mock(Context.class);

        Departamento depMock = new Departamento();
        depMock.setNome("Novo Departamento Teste");
        depMock.setCapacidade(10);
        depMock.setAtivo(true);

        Funcionario gerenteMock = new Funcionario();
        gerenteMock.setId(1);
        depMock.setGerente(gerenteMock);

        when(ctx.bodyAsClass(Departamento.class)).thenReturn(depMock);
        when(ctx.status(anyInt())).thenReturn(ctx);

        DepartamentoControler controler = new DepartamentoControler();
        controler.create(ctx);

        verify(ctx, atLeastOnce()).bodyAsClass(Departamento.class);
    }

    @Test
    public void testPutUpdateDepartamento() {
        Context ctx = mock(Context.class);
        when(ctx.pathParam("id")).thenReturn("1");

        Departamento depMock = new Departamento();
        depMock.setNome("Departamento Atualizado");

        when(ctx.bodyAsClass(Departamento.class)).thenReturn(depMock);

        when(ctx.bodyAsClass(java.util.Map.class)).thenReturn(new java.util.HashMap<>());

        when(ctx.status(anyInt())).thenReturn(ctx);

        DepartamentoControler controler = new DepartamentoControler();
        controler.update(ctx);

        verify(ctx, atLeastOnce()).pathParam("id");
    }

    @Test
    public void testDeleteDepartamento() {
        Context ctx = mock(Context.class);

        when(ctx.pathParam("id")).thenReturn("2");
        when(ctx.status(anyInt())).thenReturn(ctx);

        DepartamentoControler controler = new DepartamentoControler();
        controler.delete(ctx);

        verify(ctx, atLeastOnce()).pathParam("id");
    }
}