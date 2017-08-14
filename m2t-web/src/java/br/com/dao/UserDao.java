package br.com.dao;

import br.com.models.Diagram;
import br.com.models.Entity;
import br.com.models.UseCaseDiagram;
import br.com.models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao
        extends Dao {

    public Entity add(Entity e) {
        User user = (User) e;
        try {
            open();
            String sql = "INSERT INTO user (name, email, login, password, preferencialModeDiagram, visualImpairment, hasStudied) VALUES(?, ?, ?, ?, ?, ?, ?)";
            this.st = this.conn.prepareStatement(sql, 1);
            this.st.setString(1, user.getName());
            this.st.setString(2, user.getEmail());
            this.st.setString(3, user.getLogin());
            this.st.setString(4, user.getPassword());
            this.st.setInt(5, user.getPreferentialDiagramMode());
            this.st.setInt(6, user.getVisualImpairment().ordinal());
            this.st.setInt(7, user.getHasStudied().ordinal());
            this.st.executeUpdate();
            this.rs = this.st.getGeneratedKeys();
            if (this.rs.next()) {
                user.setId(this.rs.getInt(1));
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error inserting user:" + ex.getMessage());
        } finally {
            close();
        }
        return user;
    }

    public boolean edit(Entity e) {
        User user = (User) e;
        try {
            open();
            String sql = "";
            String pwd = user.getPassword();

            if ((pwd == null) || (pwd.equals(""))) {
                sql = "UPDATE user SET name = ?, email = ? WHERE id = ?";
            } else {
                sql = "UPDATE user SET name = ?, email = ?, password = ? WHERE id = ?";
            }
            this.st = this.conn.prepareStatement(sql);
            this.st.setString(1, user.getName());
            this.st.setString(2, user.getEmail());
            if ((pwd == null) || (pwd.equals(""))) {
                this.st.setInt(3, user.getId());
            } else {
                this.st.setString(3, user.getPassword());
                this.st.setInt(4, user.getId());
            }
            return this.st.executeUpdate() == 1;
        } catch (SQLException ex) {
            System.out.println("ERRO EDIT USER DAO: " + ex);
        } finally {
            close();
        }
        return false;
    }

    public Entity find(Entity e) {
        User user = (User) e;
        try {
            open();
            String search = user.getLogin();
            if (user.getId() != 0) {
                search = String.valueOf(user.getId());
            }
            String sql = "SELECT id, name, email, password, login FROM user ";
            sql = sql.concat(user.getId() == 0 ? " WHERE login = ? AND password = ?" : " WHERE id = ?");
            this.st = this.conn.prepareStatement(sql);
            this.st.setString(1, search);
            if (user.getId() == 0) {
                this.st.setString(2, user.getPassword());
            }
            this.rs = this.st.executeQuery();
            if (this.rs.next()) {
                user.setId(this.rs.getInt(1));
                user.setName(this.rs.getString(2));
                user.setEmail(this.rs.getString(3));
                user.setPassword(this.rs.getString(4));
                user.setLogin(this.rs.getString(5));

                sql = "SELECT diagram.id, diagram.name, diagramtype.name FROM diagram ".concat("INNER JOIN diagramtype ON (diagram.idDiagramType = diagramtype.id) ").concat("WHERE idOwner = ?");
                this.st = this.conn.prepareStatement(sql);
                this.st.setInt(1, user.getId());
                this.rs = this.st.executeQuery();

                ArrayList<Diagram> diagrsms = new ArrayList();
                while (this.rs.next()) {
                    UseCaseDiagram uc = new UseCaseDiagram();
                    int id = this.rs.getInt(1);
                    uc.setId(id);
                    uc.setName(this.rs.getString(2));

                    sql = "SELECT user.id".concat(", user.name").concat(", user.email").concat(", user.login").concat(" FROM diagram").concat(" INNER JOIN diagramcollaborator ON (diagram.id = diagramcollaborator.idDiagram)").concat(" INNER JOIN user ON (diagramcollaborator.idUser = user.id)").concat(" WHERE diagram.id = ?");
                    this.st = this.conn.prepareStatement(sql);
                    this.st.setInt(1, id);
                    ResultSet rs2ndQuery = this.st.executeQuery();
                    ArrayList<User> users = new ArrayList();
                    while (rs2ndQuery.next()) {
                        User collaborator = new User();
                        collaborator.setId(rs2ndQuery.getInt(1));
                        collaborator.setName(rs2ndQuery.getString(2));
                        collaborator.setEmail(rs2ndQuery.getString(3));
                        collaborator.setLogin(rs2ndQuery.getString(4));
                        users.add(collaborator);
                    }
                    uc.setCollaborators(users);
                    diagrsms.add(uc);
                }
                user.setMyDiagrams((ArrayList) diagrsms.clone());

                sql = "SELECT diagramcollaborator.idDiagram , diagram.name, diagram.idDiagramType, diagram.idOwner".concat(" FROM diagramcollaborator").concat(" INNER JOIN user ON (diagramcollaborator.idUser = user.id)").concat(" INNER JOIN diagram ON (diagram.id = diagramcollaborator.idDiagram)").concat(" WHERE user.id = ?");
                this.st = this.conn.prepareStatement(sql);
                this.st.setInt(1, user.getId());
                this.rs = this.st.executeQuery();

                diagrsms.clear();
                while (this.rs.next()) {
                    UseCaseDiagram uc = new UseCaseDiagram();
                    int id = this.rs.getInt(1);
                    uc.setId(id);
                    uc.setName(this.rs.getString(2));
                    diagrsms.add(uc);
                }
                user.setThirdDiagrams(diagrsms);
            } else {
                user = null;
            }
        } catch (SQLException ex) {
            user = null;
            System.out.println("ERRO FIND USER DAO: " + ex);
        } finally {
            close();
        }
        return user;
    }

    public Entity findWithoutPassword(Entity e) {
        User user = (User) e;
        try {
            open();
            String search = user.getLogin();
            if (user.getId() != 0) {
                search = String.valueOf(user.getId());
            }
            String sql = "SELECT id, name, email, password FROM user ";
            sql = sql.concat(user.getId() == 0 ? " WHERE login = ?" : " WHERE id = ?");
            this.st = this.conn.prepareStatement(sql);
            this.st.setString(1, search);
            this.rs = this.st.executeQuery();
            if (this.rs.next()) {
                user.setId(this.rs.getInt(1));
                user.setName(this.rs.getString(2));
                user.setEmail(this.rs.getString(3));
                user.setPassword(this.rs.getString(4));

                sql = "SELECT diagram.id, diagram.name, diagramtype.name FROM diagram ".concat("INNER JOIN diagramtype ON (diagram.idDiagramType = diagramtype.id) ").concat("WHERE idOwner = ?");
                this.st = this.conn.prepareStatement(sql);
                this.st.setInt(1, user.getId());
                this.rs = this.st.executeQuery();

                ArrayList<Diagram> diagrsms = new ArrayList();
                while (this.rs.next()) {
                    UseCaseDiagram uc = new UseCaseDiagram();
                    int id = this.rs.getInt(1);
                    uc.setId(id);
                    uc.setName(this.rs.getString(2));

                    sql = "SELECT user.id".concat(", user.name").concat(", user.email").concat(", user.login").concat(" FROM diagram").concat(" INNER JOIN diagramcollaborator ON (diagram.id = diagramcollaborator.idDiagram)").concat(" INNER JOIN user ON (diagramcollaborator.idUser = user.id)").concat(" WHERE diagram.id = ?");
                    this.st = this.conn.prepareStatement(sql);
                    this.st.setInt(1, id);
                    ResultSet rs2ndQuery = this.st.executeQuery();
                    ArrayList<User> users = new ArrayList();
                    while (rs2ndQuery.next()) {
                        User collaborator = new User();
                        collaborator.setId(rs2ndQuery.getInt(1));
                        collaborator.setName(rs2ndQuery.getString(2));
                        collaborator.setLogin(rs2ndQuery.getString(2));
                        collaborator.setEmail(rs2ndQuery.getString(4));
                        users.add(collaborator);
                    }
                    uc.setCollaborators(users);
                    diagrsms.add(uc);
                }
                user.setMyDiagrams((ArrayList) diagrsms.clone());

                sql = "SELECT diagramcollaborator.idDiagram , diagram.name, diagram.idDiagramType, diagram.idOwner".concat(" FROM diagramcollaborator").concat(" INNER JOIN user ON (diagramcollaborator.idUser = user.id)").concat(" INNER JOIN diagram ON (diagram.id = diagramcollaborator.idDiagram)").concat(" WHERE user.id = ?");
                this.st = this.conn.prepareStatement(sql);
                this.st.setInt(1, user.getId());
                this.rs = this.st.executeQuery();

                diagrsms.clear();
                while (this.rs.next()) {
                    UseCaseDiagram uc = new UseCaseDiagram();
                    int id = this.rs.getInt(1);
                    uc.setId(id);
                    uc.setName(this.rs.getString(2));
                    diagrsms.add(uc);
                }
                user.setThirdDiagrams(diagrsms);
            } else {
                user = null;
            }
        } catch (SQLException ex) {
            user = null;
            System.out.println("ERRO FIND USER DAO: " + ex);
        } finally {
            close();
        }
        return user;
    }

    public Entity find(User usr, int idDiagram) {
        User answer = null;
        try {
            open();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT user.id, user.name, diagramcollaborator.idDiagram")
                    .append(" FROM user")
                    .append(" INNER JOIN diagramcollaborator ON (diagramcollaborator.idUser = user.id)")
                    .append(" WHERE diagramcollaborator.idDiagram = ?")
                    .append(" AND user.email LIKE ?");

            this.st = this.conn.prepareStatement(sql.toString());
            this.st.setInt(1, idDiagram);
            this.st.setString(2, usr.getEmail());
            this.rs = this.st.executeQuery();

            if (!this.rs.next()) {

                String query = "SELECT id FROM user WHERE email LIKE ?";
                this.st = this.conn.prepareStatement(query);
                this.st.setString(1, usr.getEmail());
                this.rs = this.st.executeQuery();
                if (this.rs.next()) {
                    int idUser = this.rs.getInt(1);
                    query = "SELECT * FROM diagram WHERE id = ?";
                    this.st = this.conn.prepareStatement(query);
                    this.st.setInt(1, idDiagram);
                    this.rs = this.st.executeQuery();
                    if (this.rs.next()) {
                        query = "INSERT INTO diagramcollaborator (idUser, idDiagram) VALUES (?, ?)";
                        this.st = this.conn.prepareStatement(query);
                        this.st.setInt(1, idUser);
                        this.st.setInt(2, idDiagram);
                        if (this.st.executeUpdate() == 1) {
                            answer = new User();
                            answer.setId(idUser);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("ERRO FIND USER DAO: " + ex);
        } finally {
            close();
        }
        return answer;
    }

    public boolean find(User usr) {
        boolean ans = false;
        try {
            open();
            String sql = "SELECT email, password FROM user WHERE email LIKE ? AND password LIKE ?";
            this.st = this.conn.prepareStatement(sql);
            this.st.setString(1, usr.getEmail());
            this.st.setString(2, usr.getPassword());
            this.rs = this.st.executeQuery();
            if (this.rs.next()) {
                ans = true;
            }
        } catch (SQLException ex) {
            System.out.println("ERRO FIND USER DAO: " + ex);
        } finally {
            close();
        }
        return ans;
    }

    public boolean remove(Entity e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Entity> listAll() {
        List<Entity> users = new ArrayList();
        try {
            open();
            String sql = "SELECT id, name, email, login, password FROM user";
            this.st = this.conn.prepareStatement(sql);
            this.rs = this.st.executeQuery();
            while (this.rs.next()) {
                User user = new User();
                user.setId(this.rs.getInt(1));
                user.setName(this.rs.getString(2));
                user.setPassword(this.rs.getString(3));
                users.add(user);
            }
        } catch (SQLException ex) {
            System.out.println("ERRO FIND USER DAO: " + ex);
        } finally {
            close();
        }
        return users;
    }
}
