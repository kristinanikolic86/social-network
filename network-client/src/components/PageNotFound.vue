<template>
    <html>
    <body>
        <p>Not found!</p>
    </body>
</html>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class PageNotFound extends Vue {}
</script>

<style scoped>
body {
    font-family: 'Verdana';
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

p {
  color: #333;
  line-height: 1.8;
  font-size: 25px;
}
</style>