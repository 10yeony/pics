import {
    Bar
} from "vue-chartjs";
import axios from "axios";

export default {
    name: "DayChart",
    extends: Bar,
    data() {
        return {
            stuId: 0,
            reservationLength: 0,
            schedule: {},
            reservation: [{}],
            dayCount: new Array(0, 0, 0, 0, 0, 0, 0),
            datacollection: {
                datasets: [{
                    data: [0, 0, 0, 0, 0, 0, 0],
                    backgroundColor: [
                        "rgba(245, 100, 132, 1)",
                        "rgba(245, 100, 132, 2)",
                        "rgba(245, 100, 132, 3)",
                        "rgba(245, 100, 132, 4)",
                        "rgba(245, 100, 132, 5)",
                        "rgba(245, 100, 132, 6)",
                        "rgba(245, 100, 132, 7)"
                    ],
                    label: "Reservation per Day"
                }],
                labels: ["sun", "mon", "tue", "wed", "thu", "fri", "sat"]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        },
                        gridLines: {
                            display: true
                        }
                    }],
                    xAxes: [{
                        gridLines: {
                            display: false
                        }
                    }]
                },
                legend: {
                    display: true
                },
                responsive: true,
                maintainAspectRatio: true
            }
        }
    },
    props: ["stuIdData"],
    created: function() {
        this.stuId = this.stuIdData;
    },
    mounted() {
        axios
            .get("http://localhost:7777/studio/schedule/" + this.stuId)
            .then(response => {
                this.schedule = response.data;
                var reservation = this.schedule.reservation;
                this.reservationLength = reservation.length;
                this.reservation = this.schedule.reservation;
                this.chartData();
                this.renderChart(this.datacollection, this.options);
            })
            .catch(error => {
                console.log(error);
                this.errored = true;
            })
            .finally(() => {
                this.loading = false;
            });
    },
    methods: {
        chartData() {
            // 시작 일자와 끝나는 날 차이 구함... countDay에 1일씩 더해가면서 요일 구함
            for (let i = 0; i < this.reservationLength; i++) {
                let startDay = (new Date(this.reservation[i].startDate));
                let endDay = (new Date(this.reservation[i].endDate));
                let diff = (endDay.getTime() - startDay.getTime()) / (1000 * 60 * 60 * 24);
                // 예약 시작일의 요일 숫자로 구함 요일숫자 % 7
                if (diff >= 7) {
                    for (let i = 0; i < 7; i++) {
                        this.dayCount[i] += Math.floor(diff / 7)
                    }
                }
                for (let j = 0; j < diff % 7; j++) {
                    let tomorrow = new Date(startDay.getFullYear(), startDay.getMonth(), startDay.getDate() + j)
                    let tomorrowDay = tomorrow.getDay()
                    this.dayCount[tomorrowDay] += 1;
                }
            }
            for (let i = 0; i < 7; i++) {
                this.$set(this.datacollection.datasets[0].data, i, this.dayCount[i]);
            }
        }
    },
}