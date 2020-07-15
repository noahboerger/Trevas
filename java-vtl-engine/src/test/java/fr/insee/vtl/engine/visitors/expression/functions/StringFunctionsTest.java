package fr.insee.vtl.engine.visitors.expression.functions;

import fr.insee.vtl.engine.exceptions.UnsupportedTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StringFunctionsTest {

    private ScriptEngine engine;

    @BeforeEach
    public void setUp() {
        engine = new ScriptEngineManager().getEngineByName("vtl");
    }

    @Test
    public void testUnaryStringFunction() throws ScriptException {
        ScriptContext context = engine.getContext();
        engine.eval("trimValue := trim(\"  abc  \");");
        assertThat(context.getAttribute("trimValue")).isEqualTo("abc");
        engine.eval("ltrimValue := ltrim(\"  abc  \");");
        assertThat(context.getAttribute("ltrimValue")).isEqualTo("abc  ");
        engine.eval("rtrimValue := rtrim(\"  abc  \");");
        assertThat(context.getAttribute("rtrimValue")).isEqualTo("  abc");
        engine.eval("upperValue := upper(\"Abc\");");
        assertThat(context.getAttribute("upperValue")).isEqualTo("ABC");
        engine.eval("lowerValue := lower(\"Abc\");");
        assertThat(context.getAttribute("lowerValue")).isEqualTo("abc");
        engine.eval("lengthValue := length(\"abc\");");
        assertThat(context.getAttribute("lengthValue")).isEqualTo(3L);
    }

    @Test
    public void testSubstrAtom() throws ScriptException {
        ScriptContext context = engine.getContext();
        engine.eval("s1 := substr(\"abcde\");");
        assertThat(context.getAttribute("s1")).isEqualTo("abcde");
        engine.eval("s1 := substr(\"abcde\",1);");
        assertThat(context.getAttribute("s1")).isEqualTo("bcde");
        engine.eval("s1 := substr(\"abcde\",1,3);");
        assertThat(context.getAttribute("s1")).isEqualTo("bc");

        assertThatThrownBy(() -> {
            engine.eval("se1 := substr(\"abc\",1,2,3);");
        }).isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("too many args (3) for: substr(\"abc\",1,2,3)");
        assertThatThrownBy(() -> {
            engine.eval("se2 := substr(\"abc\",1,2,3,4,5,6);");
        }).isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("too many args (6) for: substr(\"abc\",1,2,3,4,5,6)");
    }
}
