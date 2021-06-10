package fr.insee.vtl.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import fr.insee.vtl.model.Dataset;
import fr.insee.vtl.model.Structured;

import java.io.IOException;
import java.util.Map;

/**
 * <code>ComponentDeserializer</code> is a JSON deserializer specialized for dataset components.
 */
public class ComponentDeserializer extends StdDeserializer<Structured.Component> {

    private static final Map<String, Class<?>> TYPES = Map.of(
            "STRING", String.class,
            "INTEGER", Long.class,
            "NUMBER", Double.class,
            "BOOLEAN", Boolean.class
    );

    /**
     * Base constructor.
     */
    protected ComponentDeserializer() {
        super(Structured.Component.class);
    }

    /**
     * Deserializes a JSON component into a <code>Structured.Component</code> object.
     *
     * @param p The base JSON parser.
     * @param ctxt A deserialization context.
     * @return The deserialized dataset component.
     * @throws IOException In case of problem while processing the JSON component.
     */
    @Override
    public Structured.Component deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        var node = ctxt.readTree(p);
        var name = node.get("name").asText();
        var type = node.get("type").asText();
        var role = node.get("role").asText();
        return new Dataset.Component(name, asType(type), Dataset.Role.valueOf(role));
    }

    private Class<?> asType(String type) {
        return TYPES.get(type);
    }
}
