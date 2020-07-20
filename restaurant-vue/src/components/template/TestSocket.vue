<template>
  <div style="padding-top: 100px">
    <div id="main-content" class="container">
      <div class="row">
        <div class="col-md-6">
          <form class="form-inline">
            <div class="form-group">
              <label for="connect">WebSocket connection:</label>
              <button id="connect" class="btn btn-default" type="submit" :disabled="connected == true" @click.prevent="connect">Connect</button>
              <button id="disconnect" class="btn btn-default" type="submit" :disabled="connected == false" @click.prevent="disconnect">Disconnect
              </button>
            </div>
          </form>
        </div>
        <div class="col-md-6">
          <form class="form-inline">
            <div class="form-group">
              <label for="name">What is your name?</label>
              <input type="text" id="name" class="form-control" v-model="send_message" placeholder="Your name here...">
            </div>
            <button id="send" class="btn btn-default" type="submit" @click.prevent="send">Send</button>
          </form>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <table id="conversation" class="table table-striped">
            <thead>
            <tr>
              <th>Greetings</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="item in received_messages" :key="item">
              <td>{{ item }}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import SockJS from "sockjs-client";
  import Stomp from "webstomp-client";
  import * as sta from "../../static"
  import cookies from 'vue-cookies'

  export default {
    data() {
      return {
        received_messages: [],
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
        this.$socket = new SockJS(`http://192.168.1.29:8080/rms-websocket`);
        this.$stompClient = Stomp.over(this.$socket);
        this.$stompClient.debug = () => {}
        this.$stompClient.connect(
          {
            token: cookies.get('user-token')
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
      // this.connect();
    }
  };
</script>

<style scoped>

</style>
