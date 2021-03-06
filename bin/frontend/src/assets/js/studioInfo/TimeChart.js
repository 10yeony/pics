import {
    Line
} from "vue-chartjs";
import axios from "axios";

export default {
    name: "TimeChart",
    extends: Line,
    data() {
        return {
            stuId: 0,
            reservatedLength: 0,
            schedule: {},
            reservation: [{}],
            timeCount: new Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            datacollection: {
                datasets: [{
                    data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                    pointBorderColor: "#029BE0",
                    borderColor: '#029BE0',
                    fill: false,
                    pointBackgroundColor: 'white',
                    label: "Reservation per Time"
                }],
                labels: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23]
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
                maintainAspectRatio: true,
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
                this.reservation = this.schedule.reservation
                var reservation = this.reservation;
                this.reservationLength = reservation.length;
                this.chartTimeData();
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
        chartTimeData() {
            for (let i = 0; i < this.reservationLength; i++) {
                let startTime = parseInt((new Date(this.reservation[i].startDate)).getHours());
                let endTime = parseInt((new Date(this.reservation[i].endDate)).getHours());
                for (let j = startTime; j < endTime; j++) {
                    this.timeCount[j] += 1;
                }
            }
            for (let k = 0; k < 24; k++) {
                this.$set(this.datacollection.datasets[0].data, k, this.timeCount[k]);
            }

        }
    }
}