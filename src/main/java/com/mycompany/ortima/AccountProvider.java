/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ortima;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Precondition;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sebas
 */
public class AccountProvider {
    
    CollectionReference reference;
    
    static Firestore db;
    
    public static boolean guardarAccount(String coleccion, String documento, Map<String, Object> data){
        
        db = FirestoreClient.getFirestore();
        
        try {
            DocumentReference docRef = db.collection(coleccion).document(documento);
            ApiFuture<WriteResult> result = docRef.set(data);
            System.out.println("Guardado correctamente");
            return true;
        } catch (Exception e) {
            System.out.println("Error: " +e.getMessage());
        }
        return false;
    }
    
     public static boolean actualizarAccount(String coleccion, String documento, Map<String, Object> data){
        
        db = FirestoreClient.getFirestore();
        
        try {
            DocumentReference docRef = db.collection(coleccion).document(documento);
            ApiFuture<WriteResult> result = docRef.update(data);
            System.out.println("Actualizado correctamente");
            return true;
        } catch (Exception e) {
            System.out.println("Error: " +e.getMessage());
        }
        return false;
    }
    public static boolean eliminarAccount(String coleccion, String documento){
        
        db = FirestoreClient.getFirestore();
        
        try {
            DocumentReference docRef = db.collection(coleccion).document(documento);
            ApiFuture<WriteResult> result = docRef.delete(Precondition.NONE);
            System.out.println("Eliminado correctamente");
            return true;
        } catch (Exception e) {
            System.out.println("Error: " +e.getMessage());
        }
        return false;
    }
    
    public static void cargarTablaPersona(JTable table){
        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Código Interno");
        model.addColumn("Nombre");
        model.addColumn("ID");
        model.addColumn("Telefono");
        model.addColumn("Direccion");
        model.addColumn("E-mail");
        
        try {
            CollectionReference account = ConexionFirebase.db.collection("UserAccount");
            ApiFuture<QuerySnapshot> querySnap = account.get();
            
            for (DocumentSnapshot document: querySnap.get().getDocuments()) {
                model.addRow(new Object[]{
                    document.getId(),
                    document.getString("Nombre"),
                    document.getString("ID"),
                    document.getDouble("Telefono"),
                    document.getString("Direccion"),
                    document.getString("Email")
                });
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error: "+e.getMessage());
        }
        
        table.setModel(model);
    }
        
    
}
