<?php
class Conexion {
  private $cn=null;
  function Conecta(){
      if($this->cn==null){
      $this->cn= mysqli_connect("localhost","root","","bdanime") 
              or die ("No se ha podido conectar al servidor de Base de datos");
      
      }
      return $this->cn;
  }
  function Cierra(){
      $this->cn=null;
      return $this->cn;
  }
}