<template>
</template>

<script>
  import SockJS from "sockjs-client";
  import Stomp from "webstomp-client";
  import cookies from "vue-cookies";
  import {ROOT_API, USER_TOKEN} from "../../../static";

  export default {

    data() {
      return {
        send_message: null,
        connected: false
      };
    },
    methods: {
      send() {
        console.log("Send message:" + this.send_message);
        if (this.$stompClient && this.$stompClient.connected) {
          const msg = { name: this.send_message };
          this.$stompClient.send("/app/listOfTable", JSON.stringify(msg), {});
        }
      },
      connect() {
        this.$socket = new SockJS(`${ROOT_API}/rms-websocket`);
        this.$stompClient = Stomp.over(this.$socket);
        this.$stompClient.debug = () => {}
        this.$stompClient.connect(
          {
            token: USER_TOKEN
          },
          frame => {
            this.connected = true;
            console.log(frame);
            this.$stompClient.subscribe("/topic/tables", tick => {
              console.log(JSON.parse(tick.body));
            });
          },
          error => {
            console.log(error);
            this.connected = false;
          }
        );
      },
      disconnect() {
        if (this.stompClient) {
          this.stompClient.disconnect();
        }
        this.connected = false;
      },
      tickleConnection() {
        this.connected ? this.disconnect() : this.connect();
      }
    },
    mounted() {
      this.connect();
    },
    beforeDestroy() {
      this.disconnect();
    }
  }
</script>

<style scoped>

</style>
