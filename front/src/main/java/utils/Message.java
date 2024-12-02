/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author igorl
 */
public class Message {

    private String message;
    private String title;
    private Component parent_component;

    public Message(Component parent_component, String message, String title) {
        this.message = message;
        this.title = title;
        this.parent_component = parent_component;
    }

    public void warningMessage() {
        JOptionPane.showMessageDialog(this.parent_component,
                this.message,
                this.title,
                JOptionPane.WARNING_MESSAGE);
    }
    
    public void sucessMessage() {
        JOptionPane.showMessageDialog(this.parent_component,
                this.message,
                this.title,
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    
    public void errorMessage() {
        JOptionPane.showMessageDialog(this.parent_component,
                this.message,
                this.title,
                JOptionPane.ERROR_MESSAGE);
    }
}
