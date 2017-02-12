package com.sdi.tests;

import static net.sourceforge.jwebunit.junit.JWebUnit.assertFormElementPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertTextPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertTitleEquals;
import static net.sourceforge.jwebunit.junit.JWebUnit.beginAt;
import static net.sourceforge.jwebunit.junit.JWebUnit.clickLinkWithExactText;
import static net.sourceforge.jwebunit.junit.JWebUnit.selectOptionByValue;
import static net.sourceforge.jwebunit.junit.JWebUnit.setBaseUrl;
import static net.sourceforge.jwebunit.junit.JWebUnit.setTextField;
import static net.sourceforge.jwebunit.junit.JWebUnit.submit;

import org.junit.Before;
import org.junit.Test;

public class GTDTest {

    @Before
    public void prepare() {
        setBaseUrl("http://localhost:8280");
    }

    @Test
    public void basicTest() {
        beginAt("/sesion3.MVCCasero/");
        assertTitleEquals("Inicie sesión");
        assertTextPresent("Su identificador de usuario");
        assertTextPresent("Contacto: falvarez@uniovi.es");
    }

    @Test
    public void formElementIsPresentTest()
    {
    	beginAt("/sesion3.MVCCasero/");
    	assertFormElementPresent("nombreUsuario");
     }
    
    @Test
    public void usingTheFormTestNotPrize() {
        beginAt("/sesion3.MVCCasero/");
        setTextField("nombreUsuario", "john");
        submit();
        assertTitleEquals("Mi tienduca!");
        assertTextPresent("Bienvenido (useBean) john");
        assertTextPresent("Bienvenido john [2]");
        assertTextPresent("Contacto: falvarez@uniovi.es");
        submit();
        assertTitleEquals("Tienda con login!");
        assertTextPresent("Bienvenido a la tienda, john!");
        assertTextPresent("[010], 1 unidades");
        assertTextPresent("Seguir comprando!");
        clickLinkWithExactText("Seguir comprando!");
        assertTextPresent("Siga comprando ...");
        selectOptionByValue("producto", "010");
        submit();
        assertTextPresent("Qué bien que sigas comprando, john!");
        assertTextPresent("[010], 2 unidades");
    }
    @Test
    public void usingTheFormTestWithPrize() {
        beginAt("/sesion2.JSP/");
        setTextField("nombreUsuario", "john");
        submit();
        assertTitleEquals("Mi tienduca!");
        assertTextPresent("Bienvenido (useBean) john");
        assertTextPresent("Bienvenido john [2]");
        assertTextPresent("Contacto: falvarez@uniovi.es");
        submit();
        assertTitleEquals("Tienda con login!");
        assertTextPresent("Bienvenido a la tienda, john!");
        assertTextPresent("[010], 1 unidades");
        assertTextPresent("Seguir comprando!");
        clickLinkWithExactText("Seguir comprando!");
        assertTextPresent("Siga comprando ...");
        selectOptionByValue("producto", "345");
        submit();
        assertTextPresent("Qué bien que sigas comprando, john!");
        assertTextPresent("[010], 1 unidades");
        assertTextPresent("[345], 1 unidades");
        clickLinkWithExactText("Seguir comprando!");
        assertTextPresent("Siga comprando ...");
        selectOptionByValue("producto", "554");
        submit();
        assertTextPresent("Qué bien que sigas comprando, john!");
        assertTextPresent("[010], 1 unidades");
        assertTextPresent("[345], 1 unidades");
        assertTextPresent("[554], 1 unidades");
        clickLinkWithExactText("Seguir comprando!");
        assertTextPresent("ENHORABUENA, john. HAS GANADO UN VIAJE!!!");
    }

}