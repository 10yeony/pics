import axios from "axios";
import Chat from "@/components/chat/Chat.vue"; //문의

var customer = JSON.parse(sessionStorage.getItem("customer")); //개인고객
var company = JSON.parse(sessionStorage.getItem("company")); //기업고객

export default {
    name: "Inquiry",
    components: {
        Chat
    },
    props: {
        customerMode: Boolean
    },
    data() {
        return {
            stuId: '',
            custId: '',
            inquiryFlag: true,
            inquiryList: [],

            /* 최근 대화 */
            recentChat: [],

            /* 읽지 않은 대화 개수 */
            CountOfUnreadChat: []
        }
    },
    mounted() {
        this.getRecentChat(); //최신 대화를 불러옴
        this.getCountOfUnreadChat(); //읽지 않은 메세지 개수를 불러옴

    },
    filters: {
        /* 스튜디오 이름과 고객 닉네임, 문의 내용 글자수를 15자까지만 화면에 보여줌 */
        showLimitedContent(value) {
            if (value.length > 15) {
                return value.substring(0, 15) + "... ";
            } else {
                return value;
            }

        },
        /* 문의 날짜를 연/월/일로 분할함 */
        showOnlyDate(value) {
            for (let i = 0; i < value.length; i++) {
                if (value[i] === ' ') {
                    return value.substring(0, i);
                }
            }
        },

        //파일을 첨부했을 경우 (첨부파일) 출력, 내용이 15글자 넘어갈 경우 자르기
        handleWord(value) {
            if (value === '') {
                return '(첨부파일)';
            } else {
                if (value.length > 15) {
                    return value.substring(0, 15) + "... ";
                } else {
                    return value;
                }
            }
        }
    },
    methods: {
        /* 전체 문의 내역 확인 */
        showAllChat() {
            this.$refs.chat.controlModal('show', 'chatListModal');
        },

        /* 문의 영역 Modal 보임 */
        showChatModal: function(event) {
            this.stuId = event.target.childNodes[1].innerHTML;
            this.custId = event.target.childNodes[2].innerHTML;
            console.log("자식 stuId : " + this.stuId);
            console.log("자식 custId : " + this.custId);

            /* 업체, 고객에 맞춰 각각의 값을 CompanyMyPage 또는 CustomerMyPage로 보냄 */
            if (company != null) {
                this.$emit('showComChat', this.stuId, this.custId);
            } else if (customer != null) {
                this.$emit('showCustChat', this.stuId);
            }
        },

        /* 최신 대화 가져오기 */
        getRecentChat() {
            if (customer != null) {
                axios.get('http://localhost:7777/chat/recent/cust/' + customer.custId)
                    .then((response) => {
                        if (response.data != -1) {
                            console.log('customer 최근 대화 가져오기 성공');
                            this.recentChat = response.data;
                            console.log(this.recentChat);
                            this.inquiryFlag = false;
                        }
                    })
                    .catch(() => {
                        //console.log('customer 최근 대화 가져오기 실패');
                    })
            } else if (company != null) {
                axios.get('http://localhost:7777/chat/recent/com/' + company.comId)
                    .then((response) => {
                        if (response.data != -1) {
                            //console.log('company 최근 대화 가져오기 성공');
                            this.recentChat = response.data;
                            //console.log(this.recentChat);
                            this.inquiryFlag = false;
                        }
                    })
                    .catch(() => {
                        //console.log('company 최근 대화 가져오기 실패');
                    })
            }
        },

        /* 읽지 않은 대화 개수 가져오기 */
        getCountOfUnreadChat() {
            if (company != null) { //업체 로그인
                axios.get('http://localhost:7777/chat/unread/com/' + company.comId)
                    .then((response) => {
                        if (response.data != -1) {
                            console.log('업체의 읽지 않은 대화 개수 가져오기 성공');
                            this.CountOfUnreadChat = response.data;
                            console.log(this.CountOfUnreadChat);
                        } else if (response.data == -1) {
                            this.CountOfUnreadChat = [];
                        }
                    })
                    .catch(() => {
                        console.log('업체의 읽지 않은 대화 개수 가져오기 실패');
                    })
            } else if (customer != null) { //고객 로그인
                axios.get('http://localhost:7777/chat/unread/cust/' + customer.custId)
                    .then((response) => {
                        if (response.data != -1) {
                            console.log('고객의 읽지 않은 대화 개수 가져오기 성공');
                            this.CountOfUnreadChat = response.data;
                            console.log(this.CountOfUnreadChat);
                        } else if (response.data == -1) {
                            this.CountOfUnreadChat = [];
                        }
                    })
                    .catch(() => {
                        console.log('고객의 읽지 않은 대화 개수 가져오기 실패');
                    })
            }
        },
    }
}