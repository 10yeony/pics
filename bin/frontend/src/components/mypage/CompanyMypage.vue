<template>
  <div class="mypage_container">
    <MypageNametag :customerMode="false" />
    <MypageGap categoryName="예약관리" cateogryURL="#"/>

    <!-- 예약관리-->
    <div class="mypage_card" style="height:500px;">
      <div id="calendar_header">
        <button class="arrow_btn" @click="prevWeek">
          <img src="@/assets/img/util/backward.svg">
        </button>

        <button class="today_btn" @click="moveToday">today</button>

        <button class="arrow_btn" @click="nextWeek">
          <img src="@/assets/img/util/forward.svg">
        </button>
      <span><p style="display:inline;">{{startWeek}}~{{endWeek}}</p></span>
        
        <select v-model="selectedId" @change="getStudioReservation">
          <option value="" v-if="studioFlag" disabled> 등록된 스튜디오가 없습니다. </option>
          <option value="" v-if="!studioFlag" disabled> 스튜디오를 선택해주세요. </option>
          <option v-for="(studio, index) in studioList" :key="index" :value="studio.stuId">
            {{ studio.name }}
          </option>
        </select>

      </div>

      <!-- Custom Creation Popup -->
       <modal name="creationModal" :height="230" :width="300" :styles="styles">
         <div class="popup">
          <div class="creation_contents"><label>일정 제목: </label><input type="text" placeholder="예약 불가 일정 등록" v-model="ex_title"><br></div>
          <div class="creation_contents"><label>시작 날짜</label><input type="datetime-local" :min="now" :value="start_date" @change="minuteControl"><br></div>
          <div class="creation_contents"><label>종료 날짜</label><input type="datetime-local" :min="now" :value="end_date"><br></div>
          <div class="creation_contents"><input type="checkbox" :checked="allday"><label>Allday</label></div>
          <div class="creation_contents"><button @click="addExceptionDate">저장</button></div>
        </div>
      </modal>

      <!-- Custom Detail Popup -->
       <modal name="detailModal" :height="250" :width="300" :styles="styles">
         <div class="popup">
          <h3>{{userName}}</h3><button class="chat-btn" @click="showChatModal">문의</button>
          <h4 v-html="reservationDate"></h4>
          <h4 v-if="indexFlag">예약인원 : {{totalPeople}}명</h4>
          <h4 v-if="indexFlag">비용 : {{totalPrice}}원</h4>
          <h4><span class="tui-full-calendar-icon tui-full-calendar-calendar-dot" :style="categoryColor"></span>{{reservationCategory}}</h4>
          <button id="delete" @click="onBeforeDeleteSchedule">일정취소</button>
        </div>
      </modal>

      <!-- Calendar -->
      <calendar ref="studioCalendar"
        style="height:450px;"
        :calendars="calendarList"
        :schedules="scheduleList"
        :taskView="false"
        :disableDblClick="true"
        :isReadOnly="readOnly"
        :useCreationPopup="false"
        :useDetailPopup="false"
        @beforeCreateSchedule="onBeforeCreateSchedule"
        @clickSchedule="onClickSchedule"
        @beforeUpdateSchedule="onBeforeUpdateSchedule"
        @beforeDeleteSchedule="onBeforeDeleteSchedule"
      /> 
    </div>
    <MypageGap categoryName="스튜디오 관리 +" cateogryURL="http://localhost:9999/registerStudio"/>

    <!-- 문의 내역-->
    <div class="mypage_card">
       <table>
         <tr>
          <th>종류</th> <th>스튜디오 이름</th> <th>스튜디오 위치</th> <th>스튜디오 수정</th>
        </tr>
        <tr>
          <td colspan="4" v-if="studioFlag"> 소유한 스튜디오가 없습니다.<br><a href="http://localhost:9999/registerStudio"> 새로운 스튜디오 추가하기 </a></td>
        </tr>
        <tr v-for="(studio, index) in studioList" :key="index">
          <td style="width:10%">{{studio.category.categoryName}}</td> <td style="width:35%;"><router-link :to="{name:'studioInfo', params: {stuId:studio.stuId}}">{{studio.name}}</router-link></td> 
          <td>{{studio.studioFilter.address}}</td>
          <td style="width:15%"><router-link :to="{name:'studioEdit', params: {stuId:studio.stuId}}"><button class="list_btn">수정</button></router-link></td>
        </tr>
      </table>
    </div>
    <MypageGap categoryName="문의내역" cateogryURL="/chatShow"/>

    <!-- 채팅 -->
    <Inquiry ref="inquiry" :customerMode="false" @showComChat="showComChatForInquiry"/>
    <!-- chat modal -->
      <div id="chatModal" style="display:none;">
        <div id="chatContent">
          <div id="closeChat" @click="hideChatModal">&times;</div>
          <Chat id="chatArea" ref="chat" @moveScroll="moveToScrollBottom()" />
        </div>
      </div>
  </div>
</template>

<script scoped src="@/assets/js/mypage/CompanyMypage.js"></script>
<style scoped src="@/assets/css/mypage/mypage_common.css"></style>
<style scoped src="@/assets/css/mypage/calendar.css"></style>
<style scoped src="@/assets/css/chat/ChatShow.css"></style>