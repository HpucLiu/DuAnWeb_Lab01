/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.HoaDAO;
import dao.LoaiDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Hoa;
import model.Loai;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ManageProduct", urlPatterns = {"/ManageProduct"})
@MultipartConfig
public class ManageProduct extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        LoaiDAO loaiDAO = new LoaiDAO();
        HoaDAO hoaDAO = new HoaDAO();
        String action = "LIST";
        String method = request.getMethod();

        if (request.getParameter("action") != null) {
            action = request.getParameter("action");
        }

        switch (action) {
            case "LIST":
                int pageIndex = 1;
                int pageSize = 5;
                if (request.getParameter("page") != null) {
                    pageIndex = Integer.parseInt(request.getParameter("page"));
                }

                //Tính tổng số trang có thể có
                int sumpage = (int) Math.ceil((double) hoaDAO.getAll().size() / pageSize);
                request.setAttribute("dsHoa", hoaDAO.getbyPage(pageIndex, pageSize));
                request.setAttribute("sumpage", sumpage);

                request.getRequestDispatcher("admin/list_product.jsp").forward(request, response);
                break;
            case "ADD":
                //Trả về giao diện thêm mới
                if (method.equals("GET")) {
                    request.setAttribute("dsLoai", loaiDAO.getAll());
                    request.getRequestDispatcher("admin/add_product.jsp").forward(request, response);
                } else if (method.equals("POST")) {
                    // Xử lý thêm mới
                    //b1. Lấy thông tin sản phẩm cần thêm
                    String tenHoa = request.getParameter("tenhoa");
                    double gia = Double.parseDouble(request.getParameter("gia"));
                    Part part = request.getPart("hinh");
                    int maLoai = Integer.parseInt(request.getParameter("maloai"));

                    //b2. Xử lý upload file hình ảnh
                    String realPatch = request.getServletContext().getRealPath("/assets/images/products");//Đường dẫn tuyệt đối
                    String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();//Tên 
                    part.write(realPatch + "/" + filename);

                    // Thêm vào CSDL
                    Hoa objInsert = new Hoa(0, tenHoa, gia, filename, maLoai, new Date(new java.util.Date().getTime()));
                    if (hoaDAO.Insert(objInsert)) {
                        //Thông báo thêm thành công
                        request.setAttribute("success", "Thêm thành công");
                    } else {
                        //Thông báo thêm thất bại
                        request.setAttribute("error", "Thêm thất bại");
                    }
                    // Chuyển tiếp người dùng về giao diện danh sách Sản Phẩm
                    request.getRequestDispatcher("ManageProduct?action=LIST").forward(request, response);
                }
                break;
            case "EDIT":
                // Trả về giao diện cập nhật sản phẩm
                if (method.equals("GET")) {
                    int maHoa = Integer.parseInt(request.getParameter("mahoa"));
                    request.setAttribute("dsloai", loaiDAO.getAll());
                    request.setAttribute("hoa", hoaDAO.getByID(maHoa));
                    request.getRequestDispatcher("admin/edit_product.jsp").forward(request, response);
                } else if (method.equals("POST")) {
                    // Xử lý thêm mới
                    //b1. Lấy thông tin sản phẩm cần thêm
                    int maHoa = Integer.parseInt(request.getParameter("mahoa"));
                    String tenHoa = request.getParameter("tenhoa");
                    double gia = Double.parseDouble(request.getParameter("gia"));
                    Part part = request.getPart("hinh");
                    int maloai = Integer.parseInt(request.getParameter("maloai"));
                    String fileName = request.getParameter("oldImg");

                    //b2. Xử lý upload file hình (nếu có)
                    if (part.getSize() > 0) {
                        String realPath = request.getServletContext().getRealPath("assets/images/products"); // Đường dẫn tuyệt đối
                        fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                        part.write((realPath + "/" + fileName));
                    }
                    // Thêm SP vào CSDL
                    Hoa objUpdate = new Hoa(maHoa, tenHoa, gia, fileName, maloai, new Date(new java.util.Date().getTime()));
                    if (hoaDAO.Update(objUpdate)) {
                        request.setAttribute("success", "Sửa thành công");
                    } else {
                        //Thông báo thêm thất bại

                        request.setAttribute("error", "Sửa thất bại");
                    }
                    // Chuyển tiếp người dùng về giao diện danh sách Sản Phẩm
                    request.getRequestDispatcher("ManageProduct?action=LIST").forward(request, response);
                }
                break;

            case "DELETE":

                int mahoa = Integer.parseInt(request.getParameter("mahoa"));
                if (hoaDAO.Delete(mahoa)) {
                    //Thông báo thêm thành công
                    request.setAttribute("success", "Xoá thành công");
                } else {
                    //Thông báo thêm thất bại
                    request.setAttribute("error", "Xoá thất bại");
                }
                // Chuyển tiếp người dùng về giao diện danh sách Sản Phẩm
                request.getRequestDispatcher("ManageProduct?action=LIST").forward(request, response);

                break;
        }

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ManageProduct</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageProduct at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
