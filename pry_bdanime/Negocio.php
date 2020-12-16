<?php

include './Conexion.php';

class Negocio {

    public function ListaCategoria() {
        $obj = new Conexion();
        $sql = "select id_categoria , nome_categoria,imagen from categoria ";
        $res = mysqli_query($obj->Conecta(), $sql) or die(mysqli_error($obj->Conecta()));
        $response["dato"] = array();
        while ($f = mysqli_fetch_array($res)) {
            array_push($response["dato"], array("codc" => $f[0], "nomc" => $f[1], "foto" => base64_encode($f[2])));
        }
        return $response;
    }

    public function ListaSerie($code) {
        $obj = new Conexion();
        $sql = "select id_serie , id_categoria , name_serie,imagen from serie where id_categoria=$code ";
        $res = mysqli_query($obj->Conecta(), $sql) or die(mysqli_error($obj->Conecta()));
        $response["dato"] = array();
        while ($f = mysqli_fetch_array($res)) {
            array_push($response["dato"], array("cods" => $f[0], "codc" => $f[1], "nome" => $f[2], "foto" => base64_encode($f[3])));
        }
        return $response;
    }

    public function DetalleSerie($code) {
        $obj = new Conexion();
        $sql = "SELECT id_serie ,name_serie ,precio,stock ,url,descripcion,imagen from serie where id_serie=$code ";
        $res = mysqli_query($obj->Conecta(), $sql) or die(mysqli_error($obj->Conecta()));
        $response["dato"] = array();
        if ($f = mysqli_fetch_array($res)) {
            array_push($response["dato"], array("cods" => $f[0], "nome" => $f[1], "precio" => $f[2], "stock" => $f[3],
                "url" => $f[4], "foto" => base64_encode($f[6])));
        }
        return $response;
    }

    public function IniciarSesion($correo, $pass) {
        $obj = new Conexion();
        $sql = "select * from cliente where email='$correo' and pass='$pass' ";
        $res = mysqli_query($obj->Conecta(), $sql) or die(mysqli_error($obj->Conecta()));
        $response["dato"] = array();
        if ($f = mysqli_fetch_array($res)) {
            array_push($response["dato"], array("codc" => $f[0], "dni" => $f[1], "nombre" => $f[2], "apeP" => $f[3],
                "apeM" => $f[4], "direccion" => $f[5], "telefono" => $f[6], "correo" => $f[7], "pass" => $f[8]));
        }
        return $response;
    }

    public function RegistrarCliente($DNI, $nombres, $apeP, $apeM, $direcc, $telefono, $correo, $pass) {
        $obj = new Conexion();
        $sql = "insert into cliente values(null ,'$DNI', '$nombres','$apeP' , '$apeM' ,'$direcc','$telefono','$correo','$pass' ) ";
        $res = mysqli_query($obj->Conecta(), $sql) or die(mysqli_error($obj->Conecta()));
        if ($res) {
            $opc = 'SI';
        } else {
            $opc = 'NO';
        }
        $response['dato'] = $opc;
        return $response;
    }

    public function maximoNroFactura() {
        $obj = new Conexion();
        $sql = "select ifnull(max(nro_factura),1) from factura";
        $res = mysqli_query($obj->Conecta(), $sql) or die(mysqli_error($obj->Conecta()));
        $f = mysqli_fetch_array($res);

        $response['dato'] = $f[0];
        return $response;
    }

    public function registrarFactura($codeCliente, $total) {
        $obj = new Conexion();
        $sql = "insert into factura values(null ,$codeCliente , now(),$total )";
        $res = mysqli_query($obj->Conecta(), $sql) or die(mysqli_error($obj->Conecta()));

        if ($res) {
            $opc = 'SI';
        } else {
            $opc = 'NO';
        }
        $response['dato'] = $opc;
        return $response;
    }
    
      public function registrarDetalleFactura($nroFactura, $idSerie,$precio,$cantidad) {
        $obj = new Conexion();
        $sql = "insert into detalle_factura values($nroFactura ,$idSerie , $precio,$cantidad )";
        $res = mysqli_query($obj->Conecta(), $sql) or die(mysqli_error($obj->Conecta()));

        if ($res) {
            $opc = 'SI';
        } else {
            $opc = 'NO';
        }
        $response['dato'] = $opc;
        return $response;
    }

}

?>