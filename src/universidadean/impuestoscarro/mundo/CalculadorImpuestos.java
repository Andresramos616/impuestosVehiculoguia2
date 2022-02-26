/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad Ean (Bogotá - Colombia)
 * Programa de Ingeniería de Sistemas
 * Licenciado bajo el esquema Academic Free License version 2.1
 * <p>
 * Bloque de Estudios: Desarrollo de Software
 * Ejercicio: Cálculo de Impuestos de Carros
 * Adaptado de: Proyecto CUPI2 - UNIANDES
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package universidadean.impuestoscarro.mundo;

import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * Calculador de impuestos.
 */
public class CalculadorImpuestos {
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Porcentaje de descuento por pronto pago.
     */
    public static final double PORC_DESC_PRONTO_PAGO = 10.0;

    /**
     * Valor de descuento por servicio público.
     */
    public static final double VALOR_DESC_SERVICIO_PUBLICO = 50000.0;

    /**
     * Porcentaje de descuento por traslado de cuenta.
     */
    public static final double PORC_DESC_TRASLADO_CUENTA = 5.0;

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Vehículos que maneja el calculador.
     */
    private Vehiculo[] vehiculos;

    /**
     * Vehículo actual mostrado en la aplicación.
     */
    private int posVehiculoActual;


    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Crea un calculador de impuestos, cargando la información de dos archivos. <br>
     * <b>post: </b> Se inicializaron los arreglos de vehículos y rangos.<br>
     * Se cargaron los datos correctamente a partir de los archivos.
     *
     * @throws Exception Error al cargar los archivos.
     */
    public CalculadorImpuestos() throws Exception {
        cargarVehiculos("data/vehiculos.txt");
    }

    // ----------------------------------------------------------------
    // Métodos
    // ----------------------------------------------------------------

    /**
     * Carga los datos de los vehículos que maneja el calculador de impuestos. <br>
     * <b>post: </b> Se cargan todos los vehículos del archivo con sus datos.
     *
     * @param pArchivo Nombre del archivo donde se encuentran los datos de los vehículos. pArchivo != null.
     * @throws Exception Si ocurre algún error cargando los datos.
     */
    private void cargarVehiculos(String pArchivo) throws Exception {
        String texto, valores[], sMarca, sLinea, sModelo, sImagen;
        double precio;
        int cantidad = 0;
        Vehiculo vehiculo;
        try {
            File datos = new File(pArchivo);
            FileReader fr = new FileReader(datos);
            BufferedReader lector = new BufferedReader(fr);
            texto = lector.readLine();

            cantidad = Integer.parseInt(texto);
            vehiculos = new Vehiculo[cantidad];
            posVehiculoActual = 0;

            texto = lector.readLine();
            for (int i = 0; i < vehiculos.length; i++) {
                valores = texto.split(",");

                sMarca = valores[0];
                sLinea = valores[1];
                sModelo = valores[2];
                sImagen = valores[4];
                precio = Double.parseDouble(valores[3]);

                vehiculo = new Vehiculo(sMarca, sLinea, sModelo, precio, sImagen);
                vehiculos[i] = vehiculo;
                // Siguiente línea
                texto = lector.readLine();
            }
            lector.close();
        }
        catch (IOException e) {
            throw new Exception("Error al cargar los datos almacenados de vehículos.");
        }
        catch (NumberFormatException e) {
            throw new Exception("El archivo no tiene el formato esperado.");
        }
    }

    /**
     * Calcula el pago de impuesto que debe hacer el vehículo actual. <br>
     * <b>pre:</b> Las listas de rangos y vehículos están inicializadas.
     *
     * @param descProntoPago      Indica si aplica el descuento por pronto pago.
     * @param descServicioPublico Indica si aplica el descuento por servicio público.
     * @param descTrasladoCuenta  Indica si aplica el descuento por traslado de cuenta.
     * @return Valor a pagar de acuerdo con las características del vehículo y los descuentos que se pueden aplicar.
     */

    public double calcularPago(boolean descProntoPago, boolean descServicioPublico, boolean descTrasladoCuenta) {

        double pago = 0;
        double descue1=10;
        double descue2=50000;
        double descue3=5;

        double prromedesc1=0;
        double promedescuent1=0;

        double prromedesc2=0;
        double promedescuent2=0;

        double prromedesc3=0;
        double promedescuent3=0;
        pago= buscarVehiculobtenCal();
        double pagofinal=0;

        if((descProntoPago == true && descServicioPublico) == true && descTrasladoCuenta==true) {
      prromedesc1= pago*descue1/100;
      promedescuent1=pago-prromedesc1;

      prromedesc2=promedescuent1-descue2;
      promedescuent2=prromedesc2;

      prromedesc3= promedescuent2*descue3/100;
      promedescuent3=promedescuent2-prromedesc3;
      pagofinal=promedescuent3;


        }else if (descProntoPago==true&&descServicioPublico==true){
            prromedesc1= pago*descue1/100;
            promedescuent1=pago-prromedesc1;

            prromedesc2=promedescuent1-descue2;
            promedescuent2=prromedesc2;
            pagofinal=promedescuent2;
        }
        else if (descProntoPago==true&&descTrasladoCuenta==true) {
            prromedesc1= pago*descue1/100;
            promedescuent1=pago-prromedesc1;

            prromedesc3= promedescuent1*descue3/100;
            promedescuent3=promedescuent1-prromedesc3;
            pagofinal=promedescuent3;
        }
        else if (descServicioPublico==true&&descTrasladoCuenta==true){
            prromedesc2=pago-descue2;
            promedescuent2=prromedesc2;

            prromedesc3= promedescuent2*descue3/100;
            promedescuent3=promedescuent2-prromedesc3;
            pagofinal=promedescuent3;

        }else if (descProntoPago==true){
            prromedesc1= pago*descue1/100;
            promedescuent1=pago-prromedesc1;
            pagofinal=promedescuent1;
        }
        else if (descServicioPublico==true){
            prromedesc2=pago-descue2;
            promedescuent2=prromedesc2;
            pagofinal=promedescuent2;

        }else if (descTrasladoCuenta==true){
            prromedesc3= pago*descue3/100;
            promedescuent3=pago-prromedesc3;
            pagofinal=promedescuent3;
        }else{
            pagofinal=pago;
            JOptionPane.showMessageDialog(null,"NO tiene descuentos");

        }
        // TODO: Encontrar el valor del pago de impuesto de acuerdo a los datos de entrada

        return pagofinal;

    }
    public  double buscarVehiculobtenCal(){
    double promedio = 0;
    double precio = darVehiculoActual().darPrecio();
    double minim= 30000000;
    double medmin=70000000;
    double medi=  200000000;
    double max=   200000001;

    double impus1 =0;
    double impus=0;

    double impuemin=1.5;
    double impuesegu=2;
    double impuester=2.5;
    double impuestmaxi=4;

        if(precio<=minim){
        impus1=precio*impuemin/100;
        promedio=impus1;

    }
        else if (precio<=medmin){
        impus1=precio*impuesegu/100;
        promedio=impus1;
    }
        else if (precio<=medi){
        impus1=precio*impuester/100;
        promedio=impus1;
    }
        else {
        impus1 = precio * impuestmaxi / 100;
        promedio=impus1;
    }
        return promedio;
    }

    /**
     * Retorna el primer vehículo. <br>
     * <b>post: </b> Se actualizó la posición del vehículo actual.
     *
     * @return El primer vehículo, que ahora es el vehículo actual.
     * @throws Exception Si ya se encuentra en el primer vehículo.
     */
    public Vehiculo darPrimero() throws Exception {
        if (posVehiculoActual == 0) {
            throw new Exception("Ya se encuentra en el primer vehículo.");
        }
        posVehiculoActual = 0;
        return darVehiculoActual();
    }

    /**
     * Retorna el vehículo anterior al actual. <br>
     * <b>post: </b> Se actualizó la posición del vehículo actual.
     *
     * @return El anterior vehículo, que ahora es el vehículo actual.
     * @throws Exception Si ya se encuentra en el primer vehículo.
     */
    public Vehiculo darAnterior() throws Exception {
        if (posVehiculoActual == 0) {
            throw new Exception("Se encuentra en el primer vehículo.");
        }
        posVehiculoActual--;
        return darVehiculoActual();
    }

    /**
     * Retorna el vehículo siguiente al actual. <br>
     * <b>post: </b> Se actualizó la posición del vehículo actual.
     *
     * @return El siguiente vehículo, que ahora es el vehículo actual.
     * @throws Exception Si ya se encuentra en el último vehículo
     */
    public Vehiculo darSiguiente() throws Exception {
;        if (posVehiculoActual == vehiculos.length - 1) {
            throw new Exception("Se encuentra en el último vehículo.");
        }
        posVehiculoActual++;
        return darVehiculoActual();
    }

    /**
     * Retorna el último vehículo. <br>
     * <b>post: </b> Se actualizó la posición del vehículo actual.
     *
     * @return El último vehículo, que ahora es el vehículo actual.
     * @throws Exception Si ya se encuentra en el último vehículo
     */
    public Vehiculo darUltimo() throws Exception {
        if (posVehiculoActual == vehiculos.length - 1) {
            throw new Exception("Ya se encuentra en el último vehículo.");
        }
        posVehiculoActual = vehiculos.length - 1;
        return darVehiculoActual();
    }

    /**
     * Retorna el vehículo actual.
     *
     * @return El vehículo actual.
     */
    public Vehiculo darVehiculoActual() {
        return vehiculos[posVehiculoActual];
    }

    /**
     * Busca el vehículo más caro, lo asigna como actual y lo retorna.
     *
     * @return El vehículo más caro.
     */
    public Vehiculo buscarVehiculoMasCaro() {
        Vehiculo masCaro = null;
        double precioMascaro=0;


        // TODO: Buscar el vehículo más caro del arreglo de vehículos
for (Vehiculo v: vehiculos){
    if (v.darPrecio()>precioMascaro){
        masCaro=v;
        precioMascaro=v.darPrecio();
    }
}
        return masCaro;
    }

    /**
     * Busca y retorna el primer vehículo que encuentra con la marca que se lee desde teclado. <br>
     *
     * @return El primer vehículo de la marca. Si no encuentra ninguno retorna null.
     */
    public Vehiculo buscarVehiculoPorMarca() {
        Vehiculo buscado = null;
        String marca = null;
        marca=JOptionPane.showInputDialog("ingrese la marca  a buscar");
        for (Vehiculo v: vehiculos){
            if (marca.compareTo(v.darMarca()) == 0){
                buscado=v;
            }
        }
        // TODO: Usando JOptionPane, leer la marca del vehículo a buscar

        // TODO: Retornar el primer vehículo que tiene la marca dada

        return buscado;
        }






    /**
     * Busca y retorna el vehículo de la línea buscada. <br>
     *
     * @return El vehículo de la línea, null si no encuentra ninguno.
     */
    public Vehiculo buscarVehiculoPorLinea() {
        Vehiculo buscado = null;
        String linea = null;
        linea=JOptionPane.showInputDialog("ingrese la linea a buscar");
       for (Vehiculo v: vehiculos){
           if (linea.compareTo(v.darLinea()) == 0){
            buscado=v;
           }
       }
        // TODO: Usando JOptionPane, leer la línea del vehículo a buscar

        // TODO: Buscar el primer vehículo que tiene la línea dada

        return buscado;
    }

    /**
     * Busca el vehículo más antiguo, lo asigna como actual y lo retorna.
     *
     * @return El vehículo más antiguo.
     */
    public Vehiculo buscarVehiculoMasAntiguo() {
        Vehiculo buscado = null;

        int anioMenor = 2022;

        for (int i=0;i<vehiculos.length;i++){
            Vehiculo v= vehiculos[i];
            if (Integer.parseInt(v.darAnio())<anioMenor){
                buscado = v;
                anioMenor = Integer.parseInt(v.darAnio());
                posVehiculoActual=i;
            }
        }
        // TODO: Buscar el vehículo más antiguo del sistema

        return buscado;
    }
// TODO: Buscar el vehículo más antiguo del sistema
    /**
     * Calcula el promedio de los precios de todos los automóviles que están en el sistema
     *
     * @return Promedio de precios
     */
    public double promedioPreciosVehiculos() {
        double promedio = 0.0;
double suma=0.0;
for (int i=0;i<vehiculos.length;i++) {
suma=suma+vehiculos[i].darPrecio();
}
promedio=suma/(vehiculos.length);
        return promedio;
    }
}