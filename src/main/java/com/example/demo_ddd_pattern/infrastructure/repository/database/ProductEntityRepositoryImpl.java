package com.example.demo_ddd_pattern.infrastructure.repository.database;

import com.example.demo_ddd_pattern.application.utils.CloseConnection;
import com.example.demo_ddd_pattern.domain.mapper.ProductRowMapper;
import com.example.demo_ddd_pattern.domain.object.Product;
import com.example.demo_ddd_pattern.domain.repository.ProductRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class ProductEntityRepositoryImpl implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductEntityRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAllProduct() {
        String sql = "SELECT id, name, category, price FROM products";
        try {
            return jdbcTemplate.query(sql, new ProductRowMapper());
        } catch (DataAccessException e) {
            System.err.println("Error fetching products: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public Optional<Product> findProductById(Long id) {
        String sql = "SELECT id, name, category, price FROM products WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new ProductRowMapper(), id));
        } catch (DataAccessException e) {
            System.err.println("Error fetching product by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Product create(Product product) {
        Connection con = null;
        CallableStatement cs = null;
        String query = "call createProduct(?, ?, ?)";
        try {
            // Lấy kết nối từ jdbcTemplate
            con = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();

            // Chuẩn bị callable statement cho stored procedure
            cs = con.prepareCall(query);

            // Thiết lập các tham số cho stored procedure
            cs.setString(1, product.getName());
            cs.setString(2, product.getCategory());
            cs.setDouble(3, product.getPrice());

            // Thực thi stored procedure
            cs.execute();

            // Trả về đối tượng product sau khi lưu thành công
            return product;

        } catch (SQLException e) {
            // Xử lý lỗi khi làm việc với SQL
            e.printStackTrace();
            System.err.println("Error saving product: " + e.getMessage());
            return null;
        } finally {
            // Đảm bảo đóng callable statement và connection để tránh rò rỉ tài nguyên
            CloseConnection.close(con, cs);
        }
    }

    @Override
    public void deleteProductById(Long id) {
        Connection con = null;
        CallableStatement cs = null;
        String query = "call delete_product_by_id(?)";
        try {
            // Lấy kết nối từ jdbcTemplate
            con = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();

            // Chuẩn bị callable statement cho stored procedure
            cs = con.prepareCall(query);
            cs.setLong(1, id);
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error deleting product by ID: " + e.getMessage());
        } finally {
            // Đảm bảo đóng callable statement và connection để tránh rò rỉ tài nguyên
            CloseConnection.close(con, cs);
        }
    }

    @Override
    public Product update(Product product) {
        Connection con = null;
        CallableStatement cs = null;
        String query = "call update_product(?,?,?,?)";

        try {
            // Lấy kết nối từ jdbcTemplate
            con = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            // Chuẩn bị callable statement cho stored procedure
            cs = con.prepareCall(query);

            // Thiết lập các tham số cho stored procedure
            cs.setLong(1, product.getId());
            cs.setString(2, product.getName());
            cs.setString(3, product.getCategory());
            cs.setDouble(4, product.getPrice());

            // Thực thi stored procedure
            cs.executeUpdate();

            // Trả về đối tượng product sau khi cập nhật thành công
            return product;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error updating product: " + e.getMessage());
            return null;
        } finally {
            // Đảm bảo đóng callable statement và connection để tránh rò rỉ tài nguyên
            CloseConnection.close(con, cs);
        }
    }
}
