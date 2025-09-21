/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package agendapersonal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgendaPersonal extends JFrame {

    private JTable tablaEventos;
    private DefaultTableModel modeloTabla;

    private JSpinner spinnerFecha;
    private JSpinner spinnerHora;
    private JTextField txtDescripcion;

    private JButton btnAgregar, btnEliminar, btnSalir;

    public AgendaPersonal() {
        setTitle("Agenda de Genesis");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ==== PANEL DE ENTRADA ====
        JPanel panelEntrada = new JPanel(new GridLayout(3, 2, 5, 5));

        // Fecha
        panelEntrada.add(new JLabel("Fecha (dd/MM/yyyy):"));
        spinnerFecha = new JSpinner(new SpinnerDateModel());
        spinnerFecha.setEditor(new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy"));
        panelEntrada.add(spinnerFecha);

        // Hora
        panelEntrada.add(new JLabel("Hora (HH:mm):"));
        spinnerHora = new JSpinner(new SpinnerDateModel());
        spinnerHora.setEditor(new JSpinner.DateEditor(spinnerHora, "HH:mm"));
        panelEntrada.add(spinnerHora);

        // Descripción
        panelEntrada.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        panelEntrada.add(txtDescripcion);

        // ==== PANEL DE TABLA ====
        modeloTabla = new DefaultTableModel(new Object[]{"Fecha", "Hora", "Descripción"}, 0) {
            // evitar edición directa en la tabla
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEventos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaEventos);

        // ==== PANEL DE BOTONES ====
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        btnAgregar = new JButton("Agregar");
        btnEliminar = new JButton("Eliminar seleccionado");
        btnSalir = new JButton("Salir");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnSalir);

        // ==== DISTRIBUCIÓN GENERAL ====
        setLayout(new BorderLayout(10, 10));
        add(panelEntrada, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // ==== EVENTOS ====

        // Botón AGREGAR
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEvento();
            }
        });

        // Botón ELIMINAR
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEvento();
            }
        });

        // Botón SALIR
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana
            }
        });

        // Atajo: Enter en descripción agrega
        txtDescripcion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEvento();
            }
        });
    }

    private void agregarEvento() {
        String descripcion = txtDescripcion.getText().trim();

        if (descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "La descripción no puede estar vacía",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Formateo de fecha y hora
        Date fecha = (Date) spinnerFecha.getValue();
        Date hora = (Date) spinnerHora.getValue();

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

        String strFecha = formatoFecha.format(fecha);
        String strHora = formatoHora.format(hora);

        // Insertar fila en la tabla
        modeloTabla.addRow(new Object[]{strFecha, strHora, descripcion});

        // Limpiar campo descripción
        txtDescripcion.setText("");
        txtDescripcion.requestFocus();
    }

    private void eliminarEvento() {
        int filaSeleccionada = tablaEventos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un evento primero",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el evento seleccionado?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            modeloTabla.removeRow(filaSeleccionada);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AgendaPersonal().setVisible(true);
        });
    }
}

