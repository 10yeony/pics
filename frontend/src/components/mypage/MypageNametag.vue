<template>
  <div class="tagContainer">
    <table id="nameTag">
      <tr>
        <td rowspan="2" class="userImg">
          <img :src="userImg" alt="userImg" width="70px" height="70px">
        </td>
        <td class="userName">{{username}} <span class="logout" @click="logout()">로그아웃</span></td>
        <td rowspan="2" id="userHeart">
          <router-link :to="{name:'wishList', params: {custId:userId}}" v-if="customerMode"><img src="@/assets/img/util/fullheart.svg" width="20px" height="20px" v-if="customerMode"></router-link>
        </td>
      </tr>
      <tr>
        <td class="userDetail">
          <router-link :to="link" v-if="customerMode">내 정보 수정 ></router-link>
          <router-link :to="link" v-if="!customerMode">업체 정보 수정 ></router-link>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
// @ is an alias to /src
//import axios from "axios";

export default {
  name: "MypageNametag",
  props: {
    customerMode: Boolean
  },
  data() {
    return {
      username:"",
      userId:"",
      userImg: require("@/assets/img/mypage/defaultStudio.svg"),
      link:""
    };
  },
  mounted(){
    if(sessionStorage.getItem("customer")!= null){
      var customer = JSON.parse(sessionStorage.getItem("customer"));
      this.username = customer.nickname;
      this.userId = customer.custId;
      this.userImg = customer.imgSrc;
      this.link = "/customeredit";
    }
    else if(sessionStorage.getItem("company")!=null){
      var company = JSON.parse(sessionStorage.getItem("company"));
      this.username = company.name;
      this.userId = company.comId;
      this.link = "/companyedit";
      if (company.logoImg != null) this.userImg = company.logoImg;
    }
  },
  methods:{
    logout(){
      let result = confirm("로그아웃 하시겠습니까?");
      if(result){
        if(sessionStorage.getItem("customer")!= null){
          sessionStorage.removeItem("customer");
        }
        else if(sessionStorage.getItem("company")!=null){
          sessionStorage.removeItem("company");
        }
        location.href = "/";
      }
    }
  }
};
</script>

<style scoped src="@/assets/css/mypage/nametag.css"></style>