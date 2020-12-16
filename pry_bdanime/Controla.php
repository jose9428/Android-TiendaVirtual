<?php
require './Negocio.php';

$opc = $_REQUEST['tag'];

if ($opc == "consulta1") {
    $obj = new Negocio();
    echo json_encode($obj->ListaCategoria());
}

if ($opc == "consulta2") {
    $code = $_REQUEST["code"];
    $obj = new Negocio();
    echo json_encode($obj->ListaSerie($code));
}

if ($opc == "consulta3") {
    $code = $_REQUEST["code"];
    $obj = new Negocio();
    echo json_encode($obj->DetalleSerie($code));
}

if ($opc == "consulta4") {
    $correo = $_REQUEST["correo"];
    $pass = $_REQUEST["pass"];
    $obj = new Negocio();
    echo json_encode($obj->IniciarSesion($correo, $pass));
}

if ($opc == "consulta5") {
    $DNI = $_REQUEST["dni"];
    $nombres = $_REQUEST["nombres"];
    $apeP = $_REQUEST["apeP"];
    $apeM = $_REQUEST["apeM"];
    $direcc = $_REQUEST["direcc"];
    $telefono = $_REQUEST["telefono"];
    $correo = $_REQUEST["correo"];
    $pass = $_REQUEST["pass"];
    $obj = new Negocio();
    echo json_encode($obj->RegistrarCliente($DNI, $nombres, $apeP, $apeM, $direcc, $telefono, $correo, $pass));
}

if ($opc == "consulta6") {
    $codeCliente = $_REQUEST["idCliente"];
    $total = $_REQUEST["montoTotal"];
    $obj = new Negocio();
    echo json_encode($obj->registrarFactura($codeCliente, $total));
}

if ($opc == "consulta7") {

    $obj = new Negocio();
    echo json_encode($obj->maximoNroFactura());
}

if ($opc == "consulta8") {
    $nroFactura = $_REQUEST["nroFactura"];
    $idSerie = $_REQUEST["nroSerie"];
    $precio = $_REQUEST["precio"];
    $cantidad = $_REQUEST["cantidad"];
    $obj = new Negocio();
    echo json_encode($obj->registrarDetalleFactura($nroFactura, $idSerie, $precio, $cantidad));
}
?>

