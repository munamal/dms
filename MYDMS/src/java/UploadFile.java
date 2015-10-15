/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author pravat
 */
public class UploadFile extends HttpServlet {

     private final String UPLOAD_DIRECTORY = "/home/pravat/";
   //private final String UPLOAD_DIRECTORY = "/var/lib/openshift/55b30a0d5973ca405e0000b6/app-root/data/image";
    // database connection settings
    private final String dbURL = "jdbc:mysql://localhost:3306/AppDB";
    private final String dbUser = "root";
    private final String dbPass = "mysqladmin";


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // PrintWriter out = response.getWriter();
        //process only if its multipart content
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> fileItems = upload.parseRequest(request);
                //Iterator i = fileItems.iterator();
                //while (i.hasNext()) {                }

                for (FileItem item : fileItems) {
                    if (!item.isFormField()) {

                        String name = item.getName();
                        // file name generated using current timestamp
                        String ext=name.substring(name.lastIndexOf("."));
                        name=System.currentTimeMillis()+ext;
                        System.out.println(item.getFieldName());
                        item.write(new File(UPLOAD_DIRECTORY + File.separator + name));

                        
                        String fieldName = item.getFieldName();
                        String fileName = item.getName();
                        String contentType = item.getContentType();
                        boolean isInMemory = item.isInMemory();
                        long sizeInBytes = item.getSize();
                        String filesize = Long.toString(sizeInBytes / 1024);
                        //save using jdbc
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

                        Timestamp ts = new Timestamp(new Date().getTime());
                        String sql = "INSERT INTO `Dmsfile` (`id`, `File_Name`, `File_Type`, `size`, `Folder_Id`, `dode`) VALUES (NULL, ?, ?, ?, ?,?)";
                        PreparedStatement pst;
                        pst = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
                        pst.setString(1, fileName);
                        pst.setString(2, contentType);
                        pst.setString(3, filesize);
                        pst.setString(4, "1");
                        pst.setString(5, ts.toString());
                        //int id =10;
                        pst.executeUpdate();
                        ResultSet keys = pst.getGeneratedKeys();
                        keys.next();
                        int id = keys.getInt(1);

                        pst.close();
                        //conn.close();
                        sql = "select *  from `Dmsfile`  where id=" + id;
                        Statement st = conn.createStatement();
                        ResultSet rs = st.executeQuery(sql);
                        InputStream is;
                        File f1;
                        byte[] byteArray = null;
                        while (rs.next()) {
                            String fname = rs.getString(2);
                            String fpath = UPLOAD_DIRECTORY + File.separator + fname;
                            Path path = Paths.get(fpath);
                            byteArray = Files.readAllBytes(path);

                        }

                        response.setContentType("image/jpeg");
                        response.setHeader("Content-disposition", "inline; filename='javatpoint.jpg'");
                        ServletOutputStream servletOut = response.getOutputStream();
                        BufferedOutputStream bout = new BufferedOutputStream(servletOut);
                        bout.write(byteArray);
                        bout.flush();
                        //servletOut.write(is.);
                        //pst.execute("");
                        //response using jdbc
                    } else {
                        //Plain request parameters will come here.
                        String name = item.getFieldName();
                        String value = item.getString();
                        System.out.println(name + "  " + value);
                    }
                }

                //File uploaded successfully
                request.setAttribute("message", "File Uploaded Successfully");
            } catch (Exception ex) {
                //request.setAttribute("message", "File Upload Failed due to " + ex);
            }

        } else {
            //request.setAttribute("message","Sorry this Servlet only handles file upload request");
        }

        //request.getRequestDispatcher("/result.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
