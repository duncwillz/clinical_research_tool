package dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Daily;
import model.Inpatients;
import model.Intervals;
import model.Item;
import model.Messaging;
import model.Receive;
import model.Subjects;
import model.Supplier;
import model.User;
import model.Visits;
import model.Withdrawals;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.mediator;

/**
 *
 * @author Duncan Adjei
 */
public abstract class ConnectAbstract {

    protected abstract Connection connect();
    mediator md = mediator.md();
    private final SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");

//    STORE CONNECT USER QUERIES
    public boolean create(User model) {
        try {
            String sql = "INSERT INTO user (uname, upass, ufullname, udepartment, udate) VALUES (?,?,?,?,now())";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setString(1, model.getUname());
            s.setString(2, model.getUpass());
            s.setString(3, model.getUfullname());
            s.setString(4, model.getUdepartment());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean update(User model) {
        try {
            String sql = "UPDATE user SET uname = ?, upass = ?,  ufullname = ?, udepartment = ? WHERE uid = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setString(1, model.getUname());
            s.setString(2, model.getUpass());
            s.setString(3, model.getUfullname());
            s.setString(4, model.getUdepartment());
            s.setInt(5, model.getUid());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean delete(User model) {
        try {
            String sql = "DELETE FROM user WHERE uid = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, model.getUid());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public User findUserByName(String name) {
        User model = new User();
        try {
            String sql = "SELECT * FROM user where ufullname = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setString(1, name);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                model = new User(rs.getInt("uid"));
                model.setUname(rs.getString("uname"));
                model.setUpass(rs.getString("upass"));
                model.setUfullname(rs.getString("ufullname"));
                model.setUdepartment(rs.getString("udepartment"));
                model.setUdate(rs.getDate("udate"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return model;
    }

    public List<User> findAllUser() {
        List<User> data = new ArrayList();
        try {
            String sql = "SELECT * FROM user";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                User model = new User(rs.getInt("uid"));
                model.setUname(rs.getString("uname"));
                model.setUpass(rs.getString("upass"));
                model.setUfullname(rs.getString("ufullname"));
                model.setUdepartment(rs.getString("udepartment"));
                model.setUdate(rs.getDate("udate"));
                data.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return data;
    }

    public List<User> findUserByUsernameAndPassword(User user) {
        List<User> data = new ArrayList();
        try {
            String sql = "SELECT * FROM user WHERE uname = ? AND upass = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setString(1, user.getUname());
            s.setString(2, user.getUpass());
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                User model = new User(rs.getInt("uid"));
                model.setUname(rs.getString("uname"));
                model.setUpass(rs.getString("upass"));
                model.setUfullname(rs.getString("ufullname"));
                model.setUdepartment(rs.getString("udepartment"));
                model.setUdate(rs.getDate("udate"));
                data.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return data;
    }

    public User findUserByUserId(int userId) {
        User model = new User();
        try {
            String sql = "SELECT * FROM user WHERE uid = ? ";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, userId);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                model = new User(rs.getInt("uid"));
                model.setUname(rs.getString("uname"));
                model.setUpass(rs.getString("upass"));
                model.setUfullname(rs.getString("ufullname"));
                model.setUdepartment(rs.getString("udepartment"));
                model.setUdate(rs.getDate("udate"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return model;
    }

    //    STORE CONNECT USER DIALY OR OURPATIENTS
    public boolean create(Daily model) {
        try {
            String sql = "INSERT INTO daily (dweight, dtemp, dsubjectnumber, dcasetype, dfeverstate,dtime,ddate, duser, ddatecreated) VALUES (?,?,?,?,?,?, ?,?, now())";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setDouble(1, model.getDweight());
            s.setDouble(2, model.getDtemp());
            s.setInt(3, model.getDsubjectnumber());
            s.setString(4, model.getDcasetype());
            s.setString(5, model.getDfeverstate());
            s.setTime(6, model.getDtime());
            s.setDate(7, model.getDdate());
            s.setInt(8, model.getDuser());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean update(Daily model) {
        try {
            String sql = "UPDATE daily SET dweight = ?, dtemp = ?, dsubjectnumber = ?, dcasetype = ?, dfeverstate = ?, dtime =?, ddate =? WHERE did = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setDouble(1, model.getDweight());
            s.setDouble(2, model.getDtemp());
            s.setInt(3, model.getDsubjectnumber());
            s.setString(4, model.getDcasetype());
            s.setString(5, model.getDfeverstate());
            s.setTime(6, model.getDtime());
            s.setDate(7, model.getDdate());
            s.setInt(8, model.getDid());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean delete(Daily model) {
        try {
            String sql = "DELETE FROM daily WHERE did = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, model.getDid());

            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Daily> findAllDailyRecords() {
        List<Daily> data = new ArrayList();
        try {
            String sql = "SELECT * FROM daily";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Daily model = new Daily(rs.getInt("did"));
                model.setDweight(rs.getDouble("dweight"));
                model.setDtemp(rs.getDouble("dtemp"));
                model.setDsubjectnumber(rs.getInt("dsubjectnumber"));
                model.setDcasetype(rs.getString("dcasetype"));
                model.setDfeverstate(rs.getString("dfeverstate"));
                model.setDtime(rs.getTime("dtime"));
                model.setDdate(rs.getDate("ddate"));
                model.setDuser(rs.getInt("duser"));
                model.setDdatecreated(md.dateToTimeStamp(rs.getDate("ddatecreated")));
                data.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return data;
    }

    public Daily findDailyRecordBySubject(int sn) {
        Daily model = new Daily();
        try {
            String sql = "SELECT * FROM daily WHERE dsubjectnumber = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, sn);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                model = new Daily(rs.getInt("did"));
                model.setDsubjectnumber(rs.getInt("dsubjectnumber"));
                model.setDtemp(rs.getDouble("dtemp"));
                model.setDweight(rs.getDouble("dweight"));
                model.setDtime(rs.getTime("dtime"));
                model.setDcasetype(rs.getString("dcasetype"));
                model.setDfeverstate(rs.getString("dfeverstate"));
                model.setDuser(rs.getInt("duser"));
                model.setDdate(rs.getDate("ddate"));
                model.setDdatecreated(md.dateToTimeStamp(rs.getDate("ddatecreated")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return model;
    }

    //    SUBJECT DATA
    public List<Subjects> findAllSubjects() {
        List<Subjects> data = new ArrayList();
        try {
            String sql = "SELECT * FROM subjects";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Subjects model = new Subjects(rs.getInt("sid"));
                model.setSnumber(rs.getInt("snumber"));
                model.setSgroup(rs.getString("sgroup"));
                model.setSname(rs.getString("sname"));
                model.setSdob(rs.getDate("sdob"));
                model.setSgender(rs.getString("sgender"));
                model.setScommunity(rs.getString("scommunity"));
                model.setSdatecreated(rs.getDate("sdatecreated"));
                data.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return data;
    }

    public Subjects findSubjectsByNumber(int number) {
        Subjects model = new Subjects();
        try {
            String sql = "SELECT * FROM subjects WHERE snumber = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, number);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                model = new Subjects(rs.getInt("sid"));
                model.setSnumber(rs.getInt("snumber"));
                model.setSgroup(rs.getString("sgroup"));
                model.setSname(rs.getString("sname"));
                model.setSgender(rs.getString("sgender"));
                model.setSdob(rs.getDate("sdob"));
                model.setScommunity(rs.getString("scommunity"));
                model.setSdatecreated(rs.getDate("sdatecreated"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return model;
    }

    public boolean exportAllSubjects(String path) {

        try {
            String sql = "SELECT * FROM `subjects`";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            ResultSet rs = s.executeQuery();
            String datas = "Data";
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(datas);
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("No.");
            header.createCell(1).setCellValue("Subject Number");
            header.createCell(2).setCellValue("Name");
            header.createCell(3).setCellValue("Group");
            header.createCell(4).setCellValue("DOB");
            header.createCell(5).setCellValue("Gender");
            header.createCell(6).setCellValue("Community");

            int index = 1;
            while (rs.next()) {
                XSSFRow row = sheet.createRow(index);
                row.createCell(0).setCellValue(index);
                row.createCell(1).setCellValue(rs.getString("snumber"));
                row.createCell(2).setCellValue(rs.getString("sname"));
                row.createCell(3).setCellValue(rs.getString("sgroup"));

                String da = rs.getString("sdob");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date dat = sdf1.parse(da);
                java.sql.Date sqlStartDate = new java.sql.Date(dat.getTime());
                row.createCell(4).setCellValue(format.format(sqlStartDate));
                row.createCell(5).setCellValue(rs.getString("sgender"));
                row.createCell(6).setCellValue(rs.getString("scommunity"));
                index++;
            }
            FileOutputStream output = new FileOutputStream(path + "/subjectsList - " + format.format(md.convert(LocalDate.now())) + " Report" + ".xlsx");
            wb.write(output);
            md.note("File Exported", "The Excel File has been exported to the root directory");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConnectAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnectAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ConnectAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

//    ITEM
    public boolean create(Item model) {
        try {
            String sql = "INSERT INTO item (iname, iuser, idate) VALUES (?,?,now())";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setString(1, model.getIname());
            s.setInt(2, model.getIuser());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean update(Item model) {
        try {
            String sql = "UPDATE item SET iname = ?,  iuser = ? WHERE iid = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setString(1, model.getIname());
            s.setInt(2, model.getIuser());
            s.setInt(3, model.getIid());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean delete(Item model) {
        try {
            String sql = "DELETE FROM item WHERE iid = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, model.getIid());

            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Item findItemById(int id) {
        Item model = new Item();
        try {
            String sql = "SELECT * FROM item where iid = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                model = new Item(rs.getInt("iid"), rs.getString("iname"), rs.getDate("idate"), rs.getInt("iuser"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return model;
    }

    public Item findItemByName(String itemname) {
        Item model = new Item();
        try {
            String sql = "SELECT * FROM item where iname = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setString(1, itemname);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                model = new Item(rs.getInt("iid"), rs.getString("iname"), rs.getDate("idate"), rs.getInt("iuser"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return model;
    }

    public List<Item> findAllItems() {
        List<Item> data = new ArrayList();
        try {
            String sql = "SELECT * FROM item";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Item model = new Item(rs.getInt("iid"), rs.getString("iname"), rs.getDate("idate"), rs.getInt("iuser"));
                data.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return data;
    }

    //RECEIVE 
    public boolean create(Receive model) {
        try {
            String sql = "INSERT INTO recieve (risupplier, riinvoice, rinumb,riuser,riexpiry,ridate,riitem,ridescription) VALUES (?,?,?,?,?,now(),?,?)";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setString(1, model.getRisupplier());
            s.setInt(2, model.getRiinvoice());
            s.setInt(3, model.getRinumb());
            s.setInt(4, model.getRiuser());
            s.setDate(5, model.getRiexpiry());
            s.setInt(6, model.getRiitem());
            s.setString(7, model.getRidescription());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean update(Receive model) {
        try {
            String sql = "UPDATE recieve SET risupplier = ?, riinvoice = ?, rinumb = ?,riuser = ?,riexpiry = ?,riitem = ?,ridescription = ? WHERE riid = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setString(1, model.getRisupplier());
            s.setInt(2, model.getRiinvoice());
            s.setInt(3, model.getRinumb());
            s.setInt(4, model.getRiuser());
            s.setDate(5, model.getRiexpiry());
            s.setInt(6, model.getRiitem());
            s.setString(7, model.getRidescription());
            s.setInt(8, model.getRiid());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean delete(Receive model) {
        try {
            String sql = "DELETE FROM recieve WHERE riid = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, model.getRiid());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Receive findReceiveById(int id) {
        Receive model = new Receive();
        try {
            String sql = "SELECT * FROM recieve where iid = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                model.setRiid(rs.getInt("iid"));
                model.setRisupplier(rs.getString("risupplier"));
                model.setRiexpiry(rs.getDate("riexpiry"));
                model.setRiinvoice(rs.getInt("riinvoice"));
                model.setRinumb(rs.getInt("rinumb"));
                model.setRiitem(rs.getInt("riitem"));
                model.setRidate(rs.getDate("ridate"));
                model.setRidescription(rs.getString("ridescription"));
                model.setRiuser(rs.getInt("riuser"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return model;
    }

    public List<Receive> findAllReceive() {
        List<Receive> data = new ArrayList();
        Receive model = new Receive();
        try {
            String sql = "SELECT * FROM recieve";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {

                model.setRiid(rs.getInt("riid"));
                model.setRisupplier(rs.getString("risupplier"));
                model.setRiexpiry(rs.getDate("riexpiry"));
                model.setRiinvoice(rs.getInt("riinvoice"));
                model.setRinumb(rs.getInt("rinumb"));
                model.setRiitem(rs.getInt("riitem"));
                model.setRidate(rs.getDate("ridate"));
                model.setRidescription(rs.getString("ridescription"));
                model.setRiuser(rs.getInt("riuser"));
                data.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return data;
    }

//    OUTPATIENTS
    public boolean create(Inpatients model) {
        try {
            String sql = "INSERT INTO inpatients (inpadmissionDate, inpdischargeDate,inpuserid,inpdateCreated,inpsubject) VALUES (?,?,?,now(),?)";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setDate(1, model.getInpadmissionDate());
            s.setDate(2, model.getInpdischargeDate());
            s.setInt(3, model.getInpuserid());
            s.setInt(4, model.getInpsubject());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean update(Inpatients model) {
        try {
            String sql = "UPDATE inpatients SET inpadmissionDate = ?, inpdischargeDate = ?,inpuserid = ?,inpsubject= ? WHERE inpid = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setDate(1, model.getInpadmissionDate());
            s.setDate(2, model.getInpdischargeDate());
            s.setInt(3, model.getInpuserid());
            s.setInt(4, model.getInpsubject());
            s.setInt(5, model.getInpid());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean delete(Inpatients model) {
        try {
            String sql = "DELETE FROM inpatients WHERE inpid = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, model.getInpid());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Inpatients findInpatientById(int id) {
        Inpatients model = new Inpatients();
        try {
            String sql = "SELECT * FROM inpatients where inpid = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                model.setInpid(rs.getInt("inpid"));
                model.setInpadmissionDate(rs.getDate("inpadmissionDate"));
                model.setInpdischargeDate(rs.getDate("inpdischargeDate"));
                model.setInpuserid(rs.getInt("inpuserid"));
                model.setInpdateCreated(rs.getDate("inpdateCreated"));
                model.setInpsubject(rs.getInt("inpsubject"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return model;
    }

    public List<Inpatients> findAllInpatients() {
        List<Inpatients> data = new ArrayList();
        try {
            String sql = "SELECT * FROM inpatients";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Inpatients model = new Inpatients(rs.getInt("inpid"));
                model.setInpadmissionDate(rs.getDate("inpadmissionDate"));
                model.setInpdischargeDate(rs.getDate("inpdischargeDate"));
                model.setInpuserid(rs.getInt("inpuserid"));
                model.setInpdateCreated(rs.getDate("inpdateCreated"));
                model.setInpsubject(rs.getInt("inpsubject"));
                data.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return data;
    }

    public List<Inpatients> findAllByDischarge() {
        List<Inpatients> data = new ArrayList();
        try {
            String sql = "SELECT * FROM inpatients where inpdischargeDate IS NULL";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Inpatients model = new Inpatients(rs.getInt("inpid"));
                model.setInpadmissionDate(rs.getDate("inpadmissionDate"));
                model.setInpdischargeDate(rs.getDate("inpdischargeDate"));
                model.setInpuserid(rs.getInt("inpuserid"));
                model.setInpdateCreated(rs.getDate("inpdateCreated"));
                model.setInpsubject(rs.getInt("inpsubject"));
                data.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return data;
    }

    //MESSAGEING 
    public List<Messaging> findAllMessageRecipients() {
        List<Messaging> data = new ArrayList();
        try {
            String sql = "SELECT * FROM messaging";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Messaging model = new Messaging(rs.getInt("mid"));
                model.setMname(rs.getString("mname"));
                model.setMemail(rs.getString("memail"));
                model.setMphonenumber(rs.getString("mphonenumber"));
                data.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return data;
    }

    //VISIT
    public boolean create(Visits model) {
        try {

            String sql = "INSERT INTO visits (vsuserid, vssubjectnumber,vsvisit,vsvisitdate, vsskipped, vsdatecreated) VALUES (?,?,?,?,?,now())";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, model.getVsuserid());
            s.setInt(2, model.getVssubjectnumber());
            s.setString(3, model.getVsvisit());
            s.setDate(4, model.getVsvisitdate());
            s.setInt(5, model.getVsskipped());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean update(Visits model) {
        try {
            String sql = "UPDATE visits SET vsuserid = ?, vssubjectnumber = ?,vsvisit = ?, vsskipped =?, vsvisitdate= ? WHERE vsid = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, model.getVsuserid());
            s.setInt(2, model.getVssubjectnumber());
            s.setString(3, model.getVsvisit());
            s.setInt(4, model.getVsskipped());
            s.setDate(5, model.getVsvisitdate());
            s.setInt(6, model.getVsid());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean delete(Visits model) {
        try {
            String sql = "DELETE FROM visits WHERE vsid = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, model.getVsid());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Visits> findAllVisits() {
        List<Visits> data = new ArrayList();
        try {
            String sql = "SELECT * FROM visits";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Visits model = new Visits(rs.getInt("vsid"));
                model.setVsdatecreated(rs.getDate("vsdatecreated"));
                model.setVssubjectnumber(rs.getInt("vssubjectnumber"));
                model.setVsuserid(rs.getInt("vsuserid"));
                model.setVsvisit(rs.getString("vsvisit"));
                model.setVsvisitdate(rs.getDate("vsvisitdate"));
                model.setVsskipped(rs.getInt("vsskipped"));
                data.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return data;
    }

    public List<Visits> findAllVisitsByType(String visit) {
        List<Visits> data = new ArrayList();
        try {
            String sql = "SELECT * FROM visits WHERE vsvisit = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setString(1, visit);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Visits model = new Visits(rs.getInt("vsid"));
                model.setVsdatecreated(rs.getDate("vsdatecreated"));
                model.setVssubjectnumber(rs.getInt("vssubjectnumber"));
                model.setVsuserid(rs.getInt("vsuserid"));
                model.setVsvisit(rs.getString("vsvisit"));
                model.setVsvisitdate(rs.getDate("vsvisitdate"));
                model.setVsskipped(rs.getInt("vsskipped"));
                data.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return data;
    }

    public Visits findLastVisit(Integer subjectnumber) {
        Visits model = new Visits();
        try {
            String sql = "SELECT *, CONVERT(SUBSTRING(vsvisit,6,3),UNSIGNED INTEGER) AS vsnumbers FROM visits WHERE `vssubjectnumber` = ? ORDER BY `vsnumbers` DESC LIMIT 1";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, subjectnumber);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                model = new Visits(rs.getInt("vsid"));
                model.setVsdatecreated(rs.getDate("vsdatecreated"));
                model.setVssubjectnumber(rs.getInt("vssubjectnumber"));
                model.setVsuserid(rs.getInt("vsuserid"));
                model.setVsvisit(rs.getString("vsvisit"));
                model.setVsvisitdate(rs.getDate("vsvisitdate"));
                model.setVsskipped(rs.getInt("vsskipped"));
                mediator.md().setLastVisit(rs.getInt("vsnumbers"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return model;
    }

    public boolean exportForDateAndVisit(String visit, Date date, String path) {

        try {
            String sql = "SELECT * FROM `subjects` JOIN visits ON visits.vssubjectnumber = subjects.snumber WHERE visits.vsvisitdate = ? AND visits.vsvisit = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setDate(1, date);
            s.setString(2, visit);
            ResultSet rs = s.executeQuery();
//            if(rs.findColumn("snumber")<0){
//              return false;
//            }
            String datas = "Data";
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(datas);
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("No.");
            header.createCell(1).setCellValue("Participant's ID Number");
            header.createCell(2).setCellValue("Date of Visit");
            header.createCell(3).setCellValue("Visit No.");
            header.createCell(4).setCellValue("Participant's Name");
            header.createCell(5).setCellValue("Group");

            int index = 1;
            while (rs.next()) {
                XSSFRow row = sheet.createRow(index);
                row.createCell(0).setCellValue(index);
                row.createCell(1).setCellValue(rs.getString("snumber"));
                String da = rs.getString("vsvisitdate");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date dat = sdf1.parse(da);
                java.sql.Date sqlStartDate = new java.sql.Date(dat.getTime());
                row.createCell(2).setCellValue(format.format(sqlStartDate));
                String[] v = rs.getString("vsvisit").split(" ");
                row.createCell(3).setCellValue(v[1]);
                row.createCell(4).setCellValue(rs.getString("sname"));
                v = rs.getString("sgroup").split(" ");
                row.createCell(5).setCellValue(v[0]);
                index++;
            }
//            FileOutputStream output = new FileOutputStream("C:/Users/Nana Badu Tamea/Desktop/Visit Reports/"+visit+"-"+date+" Report" + ".xlsx");
//            FileOutputStream output = new FileOutputStream("C:/Users/yaw/Desktop/Visit Reports/"+visit+" - "+ format.format(md.convert(date.toLocalDate()))+" Report" + ".xlsx");
            FileOutputStream output = new FileOutputStream(path + "/" + visit + " - " + format.format(md.convert(date.toLocalDate())) + " Report" + ".xlsx");

//            FileOutputStream output = new FileOutputStream("/Users/kwakuadjei/Desktop/Exports/"+visit+" - "+ format.format(md.convert(date.toLocalDate()))+" Report" + ".xlsx");
            wb.write(output);
            md.note("File Exported", "The Excel File has been exported to the root directory");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConnectAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnectAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ConnectAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

//    WITHDRAWALS
    public boolean create(Withdrawals model) {
        try {
            String sql = "INSERT INTO withdrawals (wsubjectnumber, wdatewithdrawal,wreasonwithdrawal,wdecisionby, wuser,wdatecreated) VALUES (?,?,?,?,now())";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, model.getWsubjectnumber());
            s.setDate(2, model.getWdatewithdrawal());
            s.setString(3, model.getWreasonwithdrawal());
            s.setString(4, model.getWdecisionby());
            s.setInt(5, model.getWuser());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean update(Withdrawals model) {
        try {
            String sql = "UPDATE withdrawals SET wsubjectnumber= ?, wdatewithdrawal= ?,wreasonwithdrawal= ?, wdecisionby = ?, wuser= ?,wdatecreated= ? WHERE wid = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, model.getWsubjectnumber());
            s.setDate(2, model.getWdatewithdrawal());
            s.setString(3, model.getWreasonwithdrawal());
            s.setString(4, model.getWdecisionby());
            s.setInt(5, model.getWuser());
            s.setInt(6, model.getWid());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean delete(Withdrawals model) {
        try {
            String sql = "DELETE FROM withdrawals WHERE wid = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, model.getWid());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Withdrawals findWithdarwalByID(int id) {
        Withdrawals model = new Withdrawals();
        try {
            String sql = "SELECT * FROM withdrawals where wsubjectnumber = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                model.setWid(rs.getInt("wid"));
                model.setWsubjectnumber(rs.getInt("wsubjectnumber"));
                model.setWdatewithdrawal(rs.getDate("wdatewithdrawal"));
                model.setWreasonwithdrawal(rs.getString("wreasonwithdrawal"));
                model.setWdecisionby(rs.getString("wdecisionby"));
                model.setWuser(rs.getInt("wuser"));
                model.setWdatecreated(rs.getDate("wdatecreated"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return model;
    }

    public List<Withdrawals> getAllWithdrawals() {
        List<Withdrawals> data = new ArrayList();
        try {
            String sql = "SELECT * FROM withdrawals";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Withdrawals model = new Withdrawals(rs.getInt("wid"));
                model.setWid(rs.getInt("wid"));
                model.setWsubjectnumber(rs.getInt("wsubjectnumber"));
                model.setWdatewithdrawal(rs.getDate("wdatewithdrawal"));
                model.setWreasonwithdrawal(rs.getString("wreasonwithdrawal"));
                model.setWdecisionby(rs.getString("wdecisionby"));
                model.setWuser(rs.getInt("wuser"));
                model.setWdatecreated(rs.getDate("wdatecreated"));
                data.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return data;
    }

    //    SUPPLIER
    public boolean create(Supplier model) {
        try {
            String sql = "INSERT INTO `supplier` (`suitem`, `susubjectid`, `sudate`, `suuserid`, `suprescriber`, `sudescription`, `sudatecreated`, `suitemnumber`) VALUES (?,?,?,?,?,?,now(),?)";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, model.getSuitem());
            s.setInt(2, model.getSusubjectid());
            s.setDate(3, model.getSudate());
            s.setInt(4, model.getSuuserid());
            s.setString(5, model.getSuprescriber());
            s.setString(6, model.getSudescription());
            s.setInt(7, model.getSuitemnumber());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean update(Supplier model) {
        try {
            String sql = "UPDATE `supplier` SET `suitem` = ?, `susubjectid` = ?, `sudate` = ?, `suuserid` = ?, `suprescriber` = ?, `sudescription` = ?, `suitemnumber` = ? WHERE `supplier`.`suid` = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, model.getSuitem());
            s.setInt(2, model.getSusubjectid());
            s.setDate(3, model.getSudate());
            s.setInt(4, model.getSuuserid());
            s.setString(5, model.getSuprescriber());
            s.setString(6, model.getSudescription());
            s.setInt(7, model.getSuitemnumber());
            s.setInt(8, model.getSuid());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean delete(Supplier model) {
        try {
            String sql = "DELETE FROM supplier WHERE suid = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, model.getSuid());
            return s.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Supplier findSupplierFromID(int id) {
        Supplier model = new Supplier();
        try {
            String sql = "SELECT * FROM supplier where suid = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                s.setInt(1, model.getSuitem());
                s.setInt(2, model.getSusubjectid());
                s.setDate(3, model.getSudate());
                s.setInt(4, model.getSuuserid());
                s.setString(5, model.getSuprescriber());
                s.setString(6, model.getSudescription());
                s.setInt(7, model.getSuitemnumber());
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return model;
    }

    public List<Supplier> getAllSuppliers() {
        List<Supplier> data = new ArrayList();
        try {
            String sql = "SELECT * FROM supplier";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Supplier model = new Supplier(rs.getInt("wid"));
                model.setSuid(rs.getInt("wid"));
                model.setSuitem(rs.getInt(""));
                model.setSuitemnumber(rs.getInt(""));
                model.setSusubjectid(rs.getInt(""));
                model.setSuuserid(rs.getInt(""));
                model.setSudate(rs.getDate(""));
                model.setSudescription(rs.getString(""));
                model.setSuprescriber(rs.getString(""));
                data.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return data;
    }

    public Double getSumOf(int itemid, String ...params) {
        double suppliers_number = 0;
        try {
            String sql = "SELECT SUM("+params[0]+") as total FROM "+params[1]+" WHERE "+params[2]+" = ?";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setInt(1, itemid);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                suppliers_number = rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return suppliers_number;
    }

//    INTERVAL
    public List<Intervals> getAllIntervals(String visit) {
        List<Intervals> data = new ArrayList();
        try {
            String sql = "SELECT visits.vssubjectnumber , visits.vsvisitdate FROM `visits` WHERE visits.vsvisit = ? AND visits.vsskipped = 0 ORDER BY visits.vssubjectnumber ASC";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            s.setString(1, visit);

            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Intervals model = new Intervals(rs.getInt("vssubjectnumber"));
                model.setPreviousVisit(rs.getDate("vsvisitdate"));
                model.setStartDate(md.calcVisitInterval(rs.getDate("vsvisitdate"), visit)[0]);
                model.setEndDate(md.calcVisitInterval(rs.getDate("vsvisitdate"), visit)[1]);
                System.out.println("This is the set endDate--->" + model.getEndDate());
                model.setDaysLeft(md.calcDaysLeft(model.getEndDate()));
                data.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return data;
    }

    public Integer getTotalOfWithdrawal() {
        Integer count = 0;
        try {
            String sql = "SELECT COUNT(wid) AS count FROM withdrawals";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }

        return count;
    }

    public boolean exportAllWithdrawals(String path) {

        try {
            String sql = "SELECT * FROM `withdrawals`";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            ResultSet rs = s.executeQuery();
            String datas = "Data";
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(datas);
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("No.");
            header.createCell(1).setCellValue("Subject Number");
            header.createCell(2).setCellValue("Withdrawal Date");
            header.createCell(3).setCellValue("Reason");
            header.createCell(4).setCellValue("Decision");

            int index = 1;
            while (rs.next()) {
                XSSFRow row = sheet.createRow(index);
                row.createCell(0).setCellValue(index);
                row.createCell(1).setCellValue(rs.getString("wsubjectnumber"));
                String da = rs.getString("wdatewithdrawal");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date dat = sdf1.parse(da);
                java.sql.Date sqlStartDate = new java.sql.Date(dat.getTime());
                row.createCell(2).setCellValue(format.format(sqlStartDate));
                row.createCell(3).setCellValue(rs.getString("wreasonwithdrawal"));
                row.createCell(4).setCellValue(rs.getString("wdecisionby"));
                index++;
            }
            FileOutputStream output = new FileOutputStream(path + "/withdrawalList - " + format.format(md.convert(LocalDate.now())) + " Report" + ".xlsx");
            wb.write(output);
            md.note("File Exported", "The Excel File has been exported to the root directory");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConnectAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnectAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ConnectAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
        
    
    public boolean exportAllVisits(String path) {

        try {
            String sql = "SELECT * FROM `visits`";
            PreparedStatement s = connect().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            s.setFetchSize(1);
            ResultSet rs = s.executeQuery();
            String datas = "Data";
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(datas);
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("No.");
            header.createCell(1).setCellValue("User");
            header.createCell(2).setCellValue("Subject Number");
            header.createCell(3).setCellValue("Visit Number");
            header.createCell(4).setCellValue("Visit Date");
            header.createCell(5).setCellValue("Visit State");
            header.createCell(6).setCellValue("Visit Created");

            int index = 1;
            while (rs.next()) {
                XSSFRow row = sheet.createRow(index);
                row.createCell(0).setCellValue(index);
                row.createCell(1).setCellValue(rs.getInt("vsuserid"));
                row.createCell(2).setCellValue(rs.getString("vssubjectnumber"));
                row.createCell(3).setCellValue(rs.getString("vsvisit"));
//                String da = rs.getString("vsvisitdate");
//                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//                java.util.Date dat = sdf1.parse(da);
//                java.sql.Date sqlStartDate = new java.sql.Date(dat.getTime());
                row.createCell(4).setCellValue(rs.getString("vsvisitdate"));
                
                row.createCell(5).setCellValue(rs.getString("vsskipped"));
                row.createCell(6).setCellValue(rs.getString("vsdatecreated"));
                index++;
            }
            FileOutputStream output = new FileOutputStream(path + "/allVisits - " + format.format(md.convert(LocalDate.now())) + " Report" + ".xlsx");
            wb.write(output);
            md.note("File Exported", "The Excel File has been exported to the root directory");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConnectAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnectAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return true;
    }
}
