package br.com.util;

import br.com.models.Actor;
import br.com.models.Element;
import br.com.models.ElementType;
import br.com.models.Relation;
import br.com.models.RelationType;
import br.com.models.UseCase;
import br.com.models.UseCaseDiagram;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 *
 * @author Davi
 */
public class Util {
    
    /**
     * Converter uma String JSON de "{"msg": "value"}" para "{\"msg\": \"value\"}"
     * @param json String JSON no formato "{"msg": "value"}"
     * @return String JSON no formato "{\"msg\": \"value\"}"
     */
    public static String toStringJSONWithEscape(Object json) {
        return json.toString().replace("\"", "\\\"");
    }
    
    /**
     * Converter uma String JSON de "{\"msg\": \"value\"}" para "{"msg": "value"}"
     * @param json String JSON no formato "{\"msg\": \"value\"}"
     * @return String JSON no formato "{"msg": "value"}"
     */
    public static String toStringJSONWithoutEscape(Object json) {
        return json.toString().replace("\\\"", "\"");
    }
    
    /**
     * Converter um diagrama do formato JSON para um objeto do tipo UseCaseDiagram
     * @param json String JSON
     * @return Objeto criado a partir da string JSON
     */
    public static UseCaseDiagram fromJSONToObject(String json) {
        UseCaseDiagram diagram = new UseCaseDiagram();
        Gson gson = new Gson();
        JsonObject diagramJson = new JsonParser().parse(json).getAsJsonObject();
        diagram.setId(diagramJson.get("id").getAsInt());
        diagram.setName(diagramJson.get("name").getAsString());
        JsonArray elements = diagramJson.getAsJsonArray("elements");
        for(JsonElement el : elements) {
            Element element = null;
            if(el.getAsJsonObject().get("type").getAsString().equals("ACTOR"))
                element = gson.fromJson(el, Actor.class);
            else if(el.getAsJsonObject().get("type").getAsString().equals("USE_CASE"))
                element = gson.fromJson(el, UseCase.class);
            diagram.getElements().add(element);
        }
        JsonArray relations = diagramJson.getAsJsonArray("relations");
        for(JsonElement rel : relations) {
            Relation relation = new Relation();
            RelationType relationType = null;
            String type = rel.getAsJsonObject().get("type").getAsString();
            switch(type) {
                case "COMMUNICATION":
                    relationType = RelationType.COMMUNICATION;
                    break;
                case "EXTENSION":
                    relationType = RelationType.EXTENSION;
                    break;
                case "INCLUSION":
                    relationType = RelationType.INCLUSION;
                    break;
                case "GENERALIZATION":
                    relationType = RelationType.GENERALIZATION;
                    break;
            }
            relation.setType(relationType);
            relation.setSource(findElementJson(diagram, rel.getAsJsonObject().get("source").getAsJsonObject()));
            relation.setTarget(findElementJson(diagram, rel.getAsJsonObject().get("target").getAsJsonObject()));
            
            diagram.getRelations().add(relation);
        }
        return diagram;
    }
    
    private static Element findElementJson(UseCaseDiagram diagram, JsonObject elementJson) {
        for(Element element: diagram.getElements()) {
            int id = elementJson.get("id").getAsInt();
            ElementType type = ElementType.getFromStringType(elementJson.get("type").getAsString());
            if (element.getId() == id && element.getType().equals(type)) {
                return element;
            }
            if (element.getId() == id && element.getType().equals(type)) {
                return element;
            }
        }
        return null;
    }
}
