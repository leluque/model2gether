package br.com.dao;

import br.com.models.Actor;
import br.com.models.Element;
import br.com.models.ElementType;
import br.com.models.Entity;
import br.com.models.Relation;
import br.com.models.RelationType;
import br.com.models.UseCase;
import br.com.models.UseCaseDiagram;
import br.com.models.User;
import br.com.util.UserUtil;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UseCaseDiagramDao extends Dao {

    public void delete(int id) {
        try {
            open();
            String sql = "delete from diagram where id=?";
            this.st = this.conn.prepareStatement(sql, 1);
            this.st.setInt(1, id);
            this.st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ERRO ADD DIAGRAM DAO: " + ex);
            ex.printStackTrace();
        } finally {
            close();
        }
    }

    public Entity add(Entity e, User user) {
        UseCaseDiagram diagram = (UseCaseDiagram) e;
        try {
            open();
            String sql = "INSERT INTO diagram (name, idDiagramType, idOwner) VALUES (?, ?, ?)";
            this.st = this.conn.prepareStatement(sql, 1);
            this.st.setString(1, diagram.getName());
            this.st.setInt(2, 1);
            this.st.setInt(3, user.getId());
            this.st.executeUpdate();
            this.rs = this.st.getGeneratedKeys();
            if (this.rs.next()) {
                diagram.setId(this.rs.getInt(1));
            }
        } catch (SQLException ex) {
            System.out.println("ERRO ADD DIAGRAM DAO: " + ex);
        } finally {
            close();
        }
        return diagram;
    }

    public Entity add(Entity e) {
        UseCaseDiagram diagram = (UseCaseDiagram) e;
        try {
            open();
            String sql = "INSERT INTO diagram (name, idDiagramType, idOwner) VALUES (?, ?, ?)";
            this.st = this.conn.prepareStatement(sql, 1);
            this.st.setString(1, diagram.getName());
            this.st.setInt(2, 1);
            this.st.setInt(3, UserUtil.getLoggedUser().getId());
            this.st.executeUpdate();
            this.rs = this.st.getGeneratedKeys();
            if (this.rs.next()) {
                diagram.setId(this.rs.getInt(1));
            }
        } catch (SQLException ex) {
            System.out.println("ERRO ADD DIAGRAM DAO: " + ex);
        } finally {
            close();
        }
        return diagram;
    }

    /**
     * @param e Diagrama a ser atualizado
     * @return True - diagrama atualizado <br />False - erro na atualizacao
     */
    @Override
    public boolean edit(Entity e) {
        UseCaseDiagram diagram = (UseCaseDiagram) e;
        try {
            open();
            String sql = "UPDATE Diagram SET name = ? WHERE id = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, diagram.getName());
            st.setInt(2, diagram.getId());
            return st.executeUpdate() == 1; // executeUpdate retorna o numero de linhas alteradas
        } catch (SQLException ex) {
            System.out.println("ERRO EDIT DIAGRAM DAO: " + ex);
        } finally {
            close();
        }
        return false;
    }

    /**
     * @param e Diagrama a ser buscado
     * @return Diagrama do banco.<br />null se nao encontrar
     */
    @Override
    public Entity find(Entity e) {
        UseCaseDiagram diagram = (UseCaseDiagram) e;
        try {
            /*
TEM QUE FAZER VARIOS SELECTS. UM PARA BUSCAR CADA ELEMENTO
             */
            open();
            // Setar autoCommit como false porque precisa salvar em mais de uma tabela
            // Se em algum momento der erro, ira fazer um rollback
            conn.setAutoCommit(false);

            // Buscar o nome do diagrama
            String sql = "SELECT Diagram.name "
                    .concat("FROM Diagram ")
                    .concat("WHERE Diagram.id = ?");
            st = conn.prepareStatement(sql);
            st.setInt(1, diagram.getId());
            rs = st.executeQuery();
            if (rs.next()) {
                diagram.setName(rs.getString(1));
            }

            // Buscar os atores
            sql = "SELECT Actor.id, Actor.name, Actor.x, Actor.y, Actor.width, Actor.height "
                    .concat("FROM Actor ")
                    .concat("WHERE Actor.idDiagram = ?");
            st = conn.prepareStatement(sql);
            st.setInt(1, diagram.getId());
            rs = st.executeQuery();
            if (rs.next()) {
                do {
                    Actor actor = new Actor();
                    actor.setId(rs.getInt(1));
                    actor.setName(rs.getString(2));
                    actor.setX(rs.getInt(3));
                    actor.setY(rs.getInt(4));
                    actor.setWidth(rs.getDouble(5));
                    actor.setHeight(rs.getDouble(6));
                    diagram.getElements().add(actor);
                } while (rs.next());
            }

            // Buscar os casos de uso
            sql = "SELECT UseCase.id, UseCase.name, UseCase.x, UseCase.y, UseCase.width, UseCase.height "
                    .concat("FROM UseCase ")
                    .concat("WHERE UseCase.idDiagram = ?");
            st = conn.prepareStatement(sql);
            st.setInt(1, diagram.getId());
            rs = st.executeQuery();
            if (rs.next()) {
                do {
                    UseCase useCase = new UseCase();
                    useCase.setId(rs.getInt(1));
                    useCase.setName(rs.getString(2));
                    useCase.setX(rs.getInt(3));
                    useCase.setY(rs.getInt(4));
                    useCase.setWidth(rs.getDouble(5));
                    useCase.setHeight(rs.getDouble(6));
                    diagram.getElements().add(useCase);
                } while (rs.next());
            }

            // Buscar as relacoes
            sql = "SELECT DISTINCT "
                    .concat("Relation.id, Relation.idRelationType, RelationElement.elementType, ")
                    .concat("Relation.idSourceElement, Relation.idTargetElement, RelationElement.id, RelationElement.idOrigin ")
                    .concat("FROM Diagram ")
                    .concat("LEFT JOIN Actor ON (Diagram.id = Actor.idDiagram) ")
                    .concat("LEFT JOIN UseCase ON (Diagram.id = UseCase.idDiagram) ")
                    .concat("LEFT JOIN Actor_RelationElement ON (Actor.id = Actor_RelationElement.idActor) ")
                    .concat("LEFT JOIN UseCase_RelationElement ON (UseCase.id = UseCase_RelationElement.idUseCase) ")
                    .concat("LEFT JOIN RelationElement ON (")
                    .concat("Actor_RelationElement.idRelationElement = RelationElement.id OR ")
                    .concat("UseCase_RelationElement.idRelationElement = RelationElement.id) ")
                    .concat("JOIN Relation ON (")
                    .concat("RelationElement.id = Relation.idSourceElement OR ")
                    .concat("RelationElement.id = Relation.idTargetElement) ")
                    .concat("WHERE Diagram.id = ?");
            st = conn.prepareStatement(sql);
            st.setInt(1, diagram.getId());
            rs = st.executeQuery();
            if (rs.next()) {
                Relation relation = new Relation();
                do {
                    relation.setId(rs.getInt(1));
                    relation.setType(RelationType.getFromIntType(rs.getInt(2)));
                    String elementType = rs.getString(3);
                    Element element = null;
                    switch (elementType) {
                        case "Ator":
                            element = findElement(diagram, rs.getInt(7), ElementType.ACTOR);
                            break;
                        case "CasoDeUso":
                            element = findElement(diagram, rs.getInt(7), ElementType.USE_CASE);
                            break;
                    }
                    if (rs.getInt(6) == rs.getInt(4)) {
                        relation.setSource(element);
                    } else if (rs.getInt(6) == rs.getInt(5)) {
                        relation.setTarget(element);
                    }
                    if (relation.getSource() != null && relation.getTarget() != null) {
                        diagram.getRelations().add(relation);
                        relation = new Relation();
                    }
                } while (rs.next());
            }

            conn.commit();
        } catch (SQLException ex) {
            try {
                conn.rollback();
                System.out.println("ERRO FIND DIAGRAM DAO: " + ex);
            } catch (SQLException ex1) {
                System.out.println("ERRO FIND DIAGRAM DAO - ROLLBACK: " + ex1);
            }
        } finally {
            try {
                conn.setAutoCommit(true);
                close();
            } catch (SQLException ex) {
                System.out.println("ERRO FIND DIAGRAM DAO - SET_AUTO_COMMIT: " + ex);
            }
        }
        return diagram;
    }

    /**
     * DEFINIR AINDA QUAL VAI SER A ESTRATEGIA DE EXCLUSAO DO DIAGRAMA
     *
     * @param e Diagrama a ser removido
     * @return True - diagrama removido <br />False - erro na remocao
     */
    @Override
    public boolean remove(Entity e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return Todos os usuarios cadastrados
     */
    @Override
    public List<Entity> listAll() {
        List<Entity> diagrams = new ArrayList<>();
        try {
            open();
            String sql = "SELECT id, name FROM Diagram";
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                UseCaseDiagram diagram = new UseCaseDiagram();
                diagram.setId(rs.getInt(1));
                diagram.setName(rs.getString(2));
                diagrams.add(diagram);
            }
        } catch (SQLException ex) {
            System.out.println("ERRO FIND DIAGRAM DAO: " + ex);
        } finally {
            close();
        }
        return diagrams;
    }

    /**
     * Salvar um caso de uso pertencente a este diagrama
     *
     * @param e Caso de Uso a ser inserido
     * @param idDiagram id do diagrama ao qual o caso de uso pertence
     * @return Caso de Uso inserido com id preenchido
     */
    public Entity saveUseCase(Entity e, int idDiagram) {
        UseCase useCase = (UseCase) e;
        try {
            open();
            String sql;
            if (useCase.getId() == 0) {  // Novo Caso de Uso
                sql = "INSERT INTO UseCase (name, x, y, width, height, idDiagram) VALUES (?, ?, ?, ?, ?, ?)";
                st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                st.setString(1, useCase.getName());
                st.setInt(2, useCase.getX());
                st.setInt(3, useCase.getY());
                st.setDouble(4, useCase.getWidth());
                st.setDouble(5, useCase.getHeight());
                st.setInt(6, idDiagram);
                st.executeUpdate();
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    useCase.setId(rs.getInt(1));
                }
            } else {                    // Atualizar Caso de Uso
                sql = "UPDATE UseCase SET name = ?, x = ?, y = ?, width = ?, height = ? "
                        .concat("WHERE id = ?");
                st = conn.prepareStatement(sql);
                st.setString(1, useCase.getName());
                st.setInt(2, useCase.getX());
                st.setInt(3, useCase.getY());
                st.setDouble(4, useCase.getWidth());
                st.setDouble(5, useCase.getHeight());
                st.setInt(6, useCase.getId());
                st.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("ERRO ADD_USE_CASE DIAGRAM DAO: " + ex);
        } finally {
            close();
        }
        return useCase;
    }

    /**
     * Salvar um ator pertencente a este diagrama
     *
     * @param e Ator a ser inserido
     * @param idDiagram id do diagrama ao qual o ator pertence
     * @return Ator inserido com id preenchido
     */
    public Entity saveActor(Entity e, int idDiagram) {
        Actor actor = (Actor) e;
        try {
            open();
            String sql;
            if (actor.getId() == 0) {    // Novo Ator
                sql = "INSERT INTO Actor (name, x, y, width, height, idDiagram) VALUES (?, ?, ?, ?, ?, ?)";
                st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                st.setString(1, actor.getName());
                st.setInt(2, actor.getX());
                st.setInt(3, actor.getY());
                st.setDouble(4, actor.getWidth());
                st.setDouble(5, actor.getHeight());
                st.setInt(6, idDiagram);
                st.executeUpdate();
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    actor.setId(rs.getInt(1));
                }
            } else {                    // Atualizar Ator
                sql = "UPDATE Actor SET name = ?, x = ?, y = ?, width = ?, height = ? "
                        .concat("WHERE id = ?");
                st = conn.prepareStatement(sql);
                st.setString(1, actor.getName());
                st.setInt(2, actor.getX());
                st.setInt(3, actor.getY());
                st.setDouble(4, actor.getWidth());
                st.setDouble(5, actor.getHeight());
                st.setInt(6, actor.getId());
                st.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("ERRO ADD_ACTOR DIAGRAM DAO: " + ex);
        } finally {
            close();
        }
        return actor;
    }

    /**
     * Salvar uma relacao pertencente a este diagrama
     *
     * @param e Relation a ser inserida
     * @return Relation inserida com id preenchido
     */
    public Entity saveRelation(Entity e) {
        Relation relation = (Relation) e;
        PreparedStatement stRelationElement = null;
        try {
            open();
            // Setar autoCommit como false porque precisa salvar em mais de uma tabela
            // Se em algum momento der erro, ira fazer um rollback
            conn.setAutoCommit(false);

            String sourceType = relation.getSource() instanceof UseCase ? "CasoDeUso" : "Ator";
            String targetType = relation.getTarget() instanceof UseCase ? "CasoDeUso" : "Ator";

            // Salvar Source na tabela RelationElement
            String sql = "INSERT INTO RelationElement (elementType, idOrigin) VALUES (?, ?)";
            stRelationElement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stRelationElement.setString(1, sourceType);
            stRelationElement.setInt(2, relation.getSource().getId()); // Nao sei se essa solucao eh adequada
            stRelationElement.executeUpdate();
            rs = stRelationElement.getGeneratedKeys();
            int idSource = 0;
            if (rs.next()) {
                idSource = rs.getInt(1);
            }

            // Salvar Target na tabela RelationElement
            sql = "INSERT INTO RelationElement (elementType, idOrigin) VALUES (?, ?)";
            stRelationElement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stRelationElement.setString(1, targetType);
            stRelationElement.setInt(2, relation.getTarget().getId()); // Nao sei se essa solucao eh adequada
            stRelationElement.executeUpdate();
            rs = stRelationElement.getGeneratedKeys();
            int idTarget = 0;
            if (rs.next()) {
                idTarget = rs.getInt(1);
            }

            // Salvar Source na tabela de Relation com o Elemento correspondente
            String relationElementTable;
            String fieldName;
            if (sourceType.equals("CasoDeUso")) {
                relationElementTable = "UseCase_RelationElement";
                fieldName = "idUseCase";
            } else {
                relationElementTable = "Actor_RelationElement";
                fieldName = "idActor";
            }
            sql = "INSERT INTO "
                    .concat(relationElementTable)
                    .concat(" (")
                    .concat(fieldName)
                    .concat(", idRelationElement) VALUES (?, ?)");
            stRelationElement = conn.prepareStatement(sql);
            stRelationElement.setInt(1, relation.getSource().getId());
            stRelationElement.setInt(2, idSource);
            stRelationElement.executeUpdate();

            // Salvar Target na tabela de Relation com o Elemento correspondente
            if (targetType.equals("CasoDeUso")) {
                relationElementTable = "UseCase_RelationElement";
                fieldName = "idUseCase";
            } else {
                relationElementTable = "Actor_RelationElement";
                fieldName = "idActor";
            }
            sql = "INSERT INTO "
                    .concat(relationElementTable)
                    .concat(" (")
                    .concat(fieldName)
                    .concat(", idRelationElement) VALUES (?, ?)");
            stRelationElement = conn.prepareStatement(sql);
            stRelationElement.setInt(1, relation.getTarget().getId());
            stRelationElement.setInt(2, idTarget);
            stRelationElement.executeUpdate();

            // Ao final, salvar na tabela relacao
            sql = "INSERT INTO Relation (idRelationType, idSourceElement, idTargetElement) VALUES (?, ?, ?)";
            st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, relation.getIntType()); // Int do Enum tem que estar com mesmo id que tipos no banco
            st.setInt(2, idSource);
            st.setInt(3, idTarget);
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if (rs.next()) {
                relation.setId(rs.getInt(1));
            }

            conn.commit();
        } catch (SQLException ex) {
            try {
                conn.rollback();
                System.out.println("ERRO ADD_ACTOR DIAGRAM DAO: " + ex);
            } catch (SQLException ex1) {
                System.out.println("ERRO ADD_ACTOR DIAGRAM DAO - ROLLBACK: " + ex1);
            }
        } finally {
            try {
                if (stRelationElement != null) {
                    stRelationElement.close();
                }
                conn.setAutoCommit(true);
                close();
            } catch (SQLException ex) {
                System.out.println("ERRO ADD_ACTOR DIAGRAM DAO - SET_AUTO_COMMIT: " + ex);
            }
        }
        return relation;
    }

    /**
     * Excluir um caso de uso
     *
     * @param e Caso de Uso para ser excluido
     * @return True - caso de uso removido <br />False - erro na remocao
     */
    public boolean removeUseCase(Entity e) {
        UseCase useCase = (UseCase) e;
        boolean result = true;
        try {
            open();
            // Setar autoCommit como false porque precisa salvar em mais de uma tabela
            // Se em algum momento der erro, ira fazer um rollback
            conn.setAutoCommit(false);
            String sql = "SELECT DISTINCT Relation.idSourceElement, Relation.idTargetElement "
                    .concat("FROM UseCase ")
                    .concat("LEFT JOIN UseCase_RelationElement ON (UseCase.id = UseCase_RelationElement.idUseCase) ")
                    .concat("LEFT JOIN RelationElement ON (UseCase_RelationElement.idRelationElement = RelationElement.id) ")
                    .concat("LEFT JOIN Relation ON (")
                    .concat("RelationElement.id = Relation.idSourceElement OR ")
                    .concat("RelationElement.id = Relation.idTargetElement) ")
                    .concat("WHERE UseCase.id = ?");
            st = conn.prepareStatement(sql);
            st.setInt(1, useCase.getId());
            rs = st.executeQuery();
            while (rs.next()) {
                String sqlAux = "DELETE FROM RelationElement WHERE id = ?";
                PreparedStatement stAux = conn.prepareStatement(sqlAux);
                stAux.setInt(1, rs.getInt(1));
                stAux.executeUpdate();
                stAux = conn.prepareStatement(sqlAux);
                stAux.setInt(1, rs.getInt(2));
                stAux.executeUpdate();
            }

            sql = "DELETE FROM UseCase WHERE id = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, useCase.getId());
            if (st.executeUpdate() != 1) {
                result = false;
            }

            conn.commit();
        } catch (SQLException ex) {
            result = false;
            try {
                conn.rollback();
                System.out.println("ERRO REMOVE_USE_CASE DIAGRAM DAO: " + ex);
            } catch (SQLException ex1) {
                System.out.println("ERRO REMOVE_USE_CASE DIAGRAM DAO - ROLLBACK: " + ex1);
            }
        } finally {
            try {
                conn.setAutoCommit(true);
                close();
            } catch (SQLException ex) {
                System.out.println("ERRO REMOVE_USE_CASE DIAGRAM DAO - SET_AUTO_COMMIT: " + ex);
            }
        }
        return result;
    }

    /**
     * Excluir um ator
     *
     * @param e Ator para ser excluido
     * @return True - ator removido <br />False - erro na remocao
     */
    public boolean removeActor(Entity e) {
        Actor actor = (Actor) e;
        boolean result = true;
        try {
            open();
            // Setar autoCommit como false porque precisa salvar em mais de uma tabela
            // Se em algum momento der erro, ira fazer um rollback
            conn.setAutoCommit(false);
            String sql = "SELECT DISTINCT Relation.idSourceElement, Relation.idTargetElement "
                    .concat("FROM Actor ")
                    .concat("LEFT JOIN Actor_RelationElement ON (Actor.id = Actor_RelationElement.idActor) ")
                    .concat("LEFT JOIN RelationElement ON (Actor_RelationElement.idRelationElement = RelationElement.id) ")
                    .concat("LEFT JOIN Relation ON (")
                    .concat("RelationElement.id = Relation.idSourceElement OR ")
                    .concat("RelationElement.id = Relation.idTargetElement) ")
                    .concat("WHERE Actor.id = ?");
            st = conn.prepareStatement(sql);
            st.setInt(1, actor.getId());
            rs = st.executeQuery();
            while (rs.next()) {
                String sqlAux = "DELETE FROM RelationElement WHERE id = ?";
                PreparedStatement stAux = conn.prepareStatement(sqlAux);
                stAux.setInt(1, rs.getInt(1));
                stAux.executeUpdate();
                stAux = conn.prepareStatement(sqlAux);
                stAux.setInt(1, rs.getInt(2));
                stAux.executeUpdate();
            }

            sql = "DELETE FROM Actor WHERE id = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, actor.getId());
            if (st.executeUpdate() != 1) {
                result = false;
            }

            conn.commit();
        } catch (SQLException ex) {
            result = false;
            try {
                conn.rollback();
                System.out.println("ERRO REMOVE_ACTOR DIAGRAM DAO: " + ex);
            } catch (SQLException ex1) {
                System.out.println("ERRO REMOVE_ACTOR DIAGRAM DAO - ROLLBACK: " + ex1);
            }
        } finally {
            try {
                conn.setAutoCommit(true);
                close();
            } catch (SQLException ex) {
                System.out.println("ERRO REMOVE_ACTOR DIAGRAM DAO - SET_AUTO_COMMIT: " + ex);
            }
        }
        return result;
    }

    /**
     * Excluir uma relacao
     *
     * @param e Relacao para ser excluida
     * @return True - relacao removida <br />False - erro na remocao
     */
    public boolean removeRelation(Entity e) {
        Relation relation = (Relation) e;
        boolean result = true;
        try {
            open();
            // Setar autoCommit como false porque precisa salvar em mais de uma tabela
            // Se em algum momento der erro, ira fazer um rollback
            conn.setAutoCommit(false);
            String sql = "SELECT DISTINCT Relation.idSourceElement, Relation.idTargetElement "
                    .concat("FROM Relation ")
                    .concat("LEFT JOIN RelationElement ON (")
                    .concat("RelationElement.id = Relation.idSourceElement OR ")
                    .concat("RelationElement.id = Relation.idTargetElement) ")
                    .concat("WHERE Relation.id = ?");
            st = conn.prepareStatement(sql);
            st.setInt(1, relation.getId());
            rs = st.executeQuery();
            if (rs.next()) {
                String sqlAux = "DELETE FROM RelationElement WHERE id = ?";
                PreparedStatement stAux = conn.prepareStatement(sqlAux);
                stAux.setInt(1, rs.getInt(1));
                stAux.executeUpdate();
                stAux = conn.prepareStatement(sqlAux);
                stAux.setInt(1, rs.getInt(2));
                stAux.executeUpdate();
            } else {
                result = false;
            }

            conn.commit();
        } catch (SQLException ex) {
            result = false;
            try {
                conn.rollback();
                System.out.println("ERRO REMOVE_RELATION DIAGRAM DAO: " + ex);
            } catch (SQLException ex1) {
                System.out.println("ERRO REMOVE_RELATION DIAGRAM DAO - ROLLBACK: " + ex1);
            }
        } finally {
            try {
                conn.setAutoCommit(true);
                close();
            } catch (SQLException ex) {
                System.out.println("ERRO REMOVE_RELATION DIAGRAM DAO - SET_AUTO_COMMIT: " + ex);
            }
        }
        return result;
    }

    /**
     * Verificar se usuario eh dono ou colaborador de um diagrama
     *
     * @param idUser ID do usuario para verificar
     * @param idDiagram ID do diagrama
     * @return True - usuario tem acesso ao diagrama<br />False - usuario nao
     * tem acesso ao diagrama
     */
    public boolean isOwnerOrCollaborator(int idUser, int idDiagram) {
        try {
            open();
            // Verificar primeiro se usuario eh dono do diagrama
            String sql = "SELECT id FROM Diagram WHERE id = ? AND idOwner = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, idUser);
            st.setInt(2, idDiagram);
            rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
            // Verificar se usuario eh colaborador do diagrama
            sql = "SELECT idUser FROM DiagramCollaborator WHERE idUser = ? AND idDiagram = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, idUser);
            st.setInt(2, idDiagram);
            rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("ERRO IS_OWNER_OR_COLLABORATOR DIAGRAM DAO: " + ex);
        } finally {
            close();
        }
        return false;
    }

    public boolean unshareDiagram(int idDiagram, int idUser) {
        String sql = "DELETE FROM diagramcollaborator WHERE idUser = ? AND idDiagram = ?";
        try {
            open();
            st = conn.prepareStatement(sql);
            st.setInt(1, idUser);
            st.setInt(2, idDiagram);
            if (st.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("ERRO unsharedDiagram DIAGRAM DAO: " + ex);
        } finally {
            close();
        }
        return false;
    }

    public boolean shareDiagram(int idDiagram, int idUser) {
        String sql = "INSERT INTO diagramcollaborator (idUser, idDiagram) VALUES (?, ?)";
        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, idUser);
            st.setInt(2, idDiagram);
            if (st.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("ERRO shareDiagram DIAGRAM DAO: " + ex);
        }
        return false;
    }

    private Element findElement(UseCaseDiagram diagram, int id, ElementType type) {
        for (Element el : diagram.getElements()) { // Atores e Casos de Uso podem ter o mesmo ID!!!
            if (el.getId() == id && el.getType().equals(type)) {
                return el;
            }
            if (el.getId() == id && el.getType().equals(type)) {
                return el;
            }
        }
        return null;
    }
}
