import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdateDataGUI {

    private JFrame frame;
    private JTextField idField, nameField, emailField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UpdateDataGUI().createGUI());
    }

    public void createGUI() {
        frame = new JFrame("Atualizar Dados");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 2));

        JLabel idLabel = new JLabel("ID do Usuário:");
        idField = new JTextField();

        JLabel nameLabel = new JLabel("Novo Nome:");
        nameField = new JTextField();

        JLabel emailLabel = new JLabel("Novo Email:");
        emailField = new JTextField();

        JButton updateButton = new JButton("Atualizar");
        updateButton.addActionListener(e -> updateData());

        frame.add(idLabel);
        frame.add(idField);
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(updateButton);

        frame.setVisible(true);
    }

    public void updateData() {
        String id = idField.getText();
        String name = nameField.getText();
        String email = emailField.getText();

        if (id.isEmpty() || name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Preencha ID, Nome e Email!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 🔥 AQUI ESTÁ O BANCO QUE VOCÊ CRIOU
        String url = "jdbc:mysql://localhost:3306/atualizar_gui?useSSL=false&serverTimezone=UTC";

        String user = "root";     // usuário do MySQL
        String password = "";     // XAMPP → normalmente vazio

        // 🔥 TABELA users criada no SQL fornecido
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setInt(3, Integer.parseInt(id));

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(frame,
                        "Dados atualizados com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Usuário não encontrado!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame,
                    "Erro ao atualizar os dados!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
