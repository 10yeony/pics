<template>
  <div class="mainsecond_container">
    <div class="word_container">
      <div class="word_item">추천 인기공간</div>
      <div class="word_item">
        <a href>더보기</a>
      </div>
    </div>
    <!-- 캐라셀 -->
    <div class="mainsecond_carousel">
      <carousel
        v-if="studio_infos && studio_infos.length"
        v-bind:items="num"
        :margin="5"
        :loop="true"
        :nav="false"
      >
        <a href v-for="(studio,index) in studio_infos" :key="index">
          <div class="row">
            <div class="card" @click.prevent="moveToComInfo(studio.stu_id)">
              <div class="card-image">
                <img :src="getImgUrl(studio.main_img)" />
              </div>
              <div class="card-content">
                <div class="name">
                  {{studio.name |titleSplit}}
                  <div class="category">
                    {{ studio.category_name }}/
                    {{studio.address | category}}
                  </div>
                </div>
                <div class="price">
                  {{ studio.unit_price | currency }}
                  <span>원/시간</span>
                </div>
              </div>
            </div>
          </div>
        </a>
      </carousel>
    </div>
  </div>
</template>

<script scoped src="@/assets/js/main/mainsecond.js"></script>
<style scoped src="../../assets/css/remove_css.css">
</style>
<style scoped src="materialize-css/dist/css/materialize.min.css">
</style>
<style scoped src="material-design-icons/iconfont/material-icons.css">
</style>
<style scoped src="@/assets/css/main/mainsecond.css">
</style>
