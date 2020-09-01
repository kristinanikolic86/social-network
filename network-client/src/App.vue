<template>
<html>
  <body>
<div id="app">
  <div id="nav">
   <h1>Social Network</h1>
</div>
  <router-view/>
</div>
  </body>
</html>
</template>


<style>

template, html, body {
  margin: 0;
  padding: 0;
}
#app {
 font-family: 'Verdana';
  margin: 0;
  padding: 0;
}

 #app #nav  {
  width:100%;
  background-color:#FF8C00;
  height: 66px;
}

 #app #nav h1{
  float:left;
  padding: 6px 10px;
  margin-top: 12px;
  margin-right: 16px;
  color:white;
  cursor: pointer;
 }

  #app #nav h1:hover{
    color:rgb(240, 236, 236);
  }





</style>
