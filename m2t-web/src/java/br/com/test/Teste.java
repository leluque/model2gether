/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.test;

import br.com.dao.UseCaseDiagramDao;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import br.com.models.Actor;
import br.com.models.Element;
import br.com.models.Relation;
import br.com.models.RelationType;
import br.com.models.UseCase;
import br.com.models.UseCaseDiagram;
import br.com.util.Util;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Particular
 */
public class Teste {
    
    public static void main(String[] args) {
//        gsonTest();
        
//        relationTest();
        
//        diagramDaoTest();
        
//        getDiagramTest();
        
        convertDiagramTest();
    }
    
    public static void relationTest() {
        Gson gson = new Gson();
        
        String relationGson = "{\"source\":null,\"target\":null,\"type\":\"COMMUNICATION\",\"id\":10}";
        
        Relation rel = gson.fromJson(relationGson, Relation.class);
        
        System.out.println(gson.toJson(rel));
    }
    
    public static void gsonTest() {
        Actor actor1 = new Actor("Ator 1", 0, 0, 0, 0);
        Actor actor2 = new Actor("Ator 2", 0, 0, 0, 0);
        List<Element>  el = new ArrayList<>();
        el.add(actor1);
        el.add(actor2);
        UseCaseDiagram diagram = new UseCaseDiagram("Novo diagrama", el, null);
        Relation r = new Relation();
        r.setSource(actor1);
        r.setTarget(actor2);
        r.setType(RelationType.GENERALIZATION);
        List<Relation> rels = new ArrayList<>();
        rels.add(r);
        diagram.setRelations(rels);
        Gson gson = new Gson();
        
        String sGson = gson.toJson(diagram, UseCaseDiagram.class);
        
        System.out.println(sGson);
        
        JsonObject obj = new JsonObject();
        JsonParser parser = new JsonParser();
        obj.addProperty("msg", "create");
        obj.add("diagram", parser.parse(sGson).getAsJsonObject());
        obj.addProperty("diagram2", gson.toJson(diagram));
        obj.addProperty("diagram3", 
                Util.toStringJSONWithoutEscape(gson.toJson(diagram)));
        
        System.out.println("Mensagem: " + obj.get("msg"));
        System.out.println("");
        
        JsonObject jDiagram = obj.getAsJsonObject("diagram");
        JsonArray elements = jDiagram.getAsJsonArray("elements");
        
        for (int i = 0; i < elements.size(); i++) {
            System.out.println("Elemento "+(i+1)+": " + elements.get(i));
            Element ell = new Gson().fromJson(elements.get(i), Actor.class);
            System.out.println("IGUAL? " + elements.get(i).getAsJsonObject().get("type").toString().equals("ACTOR"));
            System.out.println("Tipo: " + elements.get(i).getAsJsonObject().get("type").toString());
        }
      
        System.out.println("\nCOMPARAÇÃO DO TIPO: "  + RelationType.COMMUNICATION.toString().equals("COMMUNICATION") + " TIPO: " + RelationType.COMMUNICATION.name());
      
        System.out.println(obj);
    }
    
    public static void diagramDaoTest() {
        List<Element> elements = new ArrayList<>();
        List<Relation> relations = new ArrayList<>();
        UseCaseDiagram diagram = new UseCaseDiagram("Clínica Médica", elements, relations);

        Actor paciente = new Actor("Paciente", 25, 171, 50, 70);
        Actor secretaria = new Actor("Secretaria", 595, 29, 50, 70);
        Actor doutor = new Actor("Doutor", 585, 352, 50, 70);
        
        UseCase cancCons = new UseCase("Cancelar Consulta", 215, 29, 205.575672, 35);
        UseCase marcCons = new UseCase("Marcar Consulta", 225, 153, 182.412216, 35);
        UseCase procReg = new UseCase("Procurar Registro do Paciente", 450, 218, 205.575672, 47.774628);
        UseCase pedRem = new UseCase("Pedir Remédio", 240, 314, 159.24876, 28.95432);
        
        Relation r1 = new Relation(paciente, cancCons, RelationType.COMMUNICATION);
        Relation r2 = new Relation(paciente, marcCons, RelationType.COMMUNICATION);
        Relation r3 = new Relation(paciente, pedRem, RelationType.COMMUNICATION);
        Relation r4 = new Relation(secretaria, cancCons, RelationType.COMMUNICATION);
        Relation r5 = new Relation(secretaria, marcCons, RelationType.COMMUNICATION);
        Relation r6 = new Relation(doutor, pedRem, RelationType.COMMUNICATION);
        Relation r7 = new Relation(marcCons, procReg, RelationType.INCLUSION);
        Relation r8 = new Relation(pedRem, procReg, RelationType.INCLUSION);
        Relation r9 = new Relation(doutor, secretaria, RelationType.GENERALIZATION);
        
        elements.add(paciente);
        elements.add(secretaria);
        elements.add(doutor);
        elements.add(cancCons);
        elements.add(marcCons);
        elements.add(procReg);
        elements.add(pedRem);
        
        relations.add(r1);
        relations.add(r2);
        relations.add(r3);
        relations.add(r4);
        relations.add(r5);
        relations.add(r6);
        relations.add(r7);
        relations.add(r8);
        relations.add(r9);
        
        
        UseCaseDiagramDao dao = new UseCaseDiagramDao();
        
        diagram = (UseCaseDiagram) dao.add(diagram);
        
        dao.saveActor(paciente, diagram.getId());
        dao.saveActor(secretaria, diagram.getId());
        dao.saveActor(doutor, diagram.getId());
        
        dao.saveUseCase(cancCons, diagram.getId());
        dao.saveUseCase(marcCons, diagram.getId());
        dao.saveUseCase(procReg, diagram.getId());
        dao.saveUseCase(pedRem, diagram.getId());
        
        dao.saveRelation(r1);
        dao.saveRelation(r2);
        dao.saveRelation(r3);
        dao.saveRelation(r4);
        dao.saveRelation(r5);
        dao.saveRelation(r6);
        dao.saveRelation(r7);
        dao.saveRelation(r8);
        dao.saveRelation(r9);
    }
    
    public static void getDiagramTest() {
        UseCaseDiagram diagram = new UseCaseDiagram();
        diagram.setId(1);
        
        UseCaseDiagramDao dao = new UseCaseDiagramDao();
        
        diagram = (UseCaseDiagram) dao.find(diagram);
        
        System.out.println(diagram);
    }
    
    public static String getDiagramText() {
        return 
            "Diagrama \"Clínica Médica\"\n" +
            "  Atores\n" +
            "    \"Paciente\",\n" +
            "    \"Secretaria\",\n" +
            "    \"Doutor\".\n" +
            "  Casos de Uso\n" +
            "    \"Cancelar Consulta\",\n" +
            "    \"Marcar Consulta\",\n" +
            "    \"Procurar Registro do Paciente\",\n" +
            "    \"Pedir Remédio\".\n" +
            "  Relações\n" +
            "    \"Paciente\" conecta com \"Cancelar Consulta\",\n" +
            "    \"Paciente\" conecta com \"Marcar Consulta\",\n" +
            "    \"Paciente\" conecta com \"Pedir Remédio\",\n" +
            "    \"Secretaria\" conecta com \"Cancelar Consulta\",\n" +
            "    \"Secretaria\" conecta com \"Marcar Consulta\",\n" +
            "    \"Doutor\" conecta com \"Pedir Remédio\",\n" +
            "    \"Marcar Consulta\" include \"Procurar Registro do Paciente\",\n" +
            "    \"Pedir Remédio\" include \"Procurar Registro do Paciente\",\n" +
            "    \"Doutor\" herda de \"Secretaria\".";
    }
    
    public static void convertDiagramTest() {
        UseCaseDiagram diagram = new UseCaseDiagram();
        diagram.setId(1);
        
        UseCaseDiagramDao dao = new UseCaseDiagramDao();
        
        diagram = (UseCaseDiagram) dao.find(diagram);
        
        System.out.println(diagram);
        
        diagram = Util.fromJSONToObject(new Gson().toJson(diagram));
        
        System.out.println(diagram);
    }
}
