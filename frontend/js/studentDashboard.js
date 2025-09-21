    $(document).ready(function () {
    viewExams();
});

    function viewExams() {
    $.ajax({
        url: "http://localhost:8080/api/v1/exam/get",
        method: "GET",
        success: function(response) {
            let cardsContainer = $("#examCardsContainer");
            cardsContainer.empty();

            let upcomingCount = 0;
            let completedCount = 0;
            let totalScore = 0;
            let scoreCount = 0;
            let nextExamDesc = "N/A";
            let nextExamDate = null;

            if (response && Array.isArray(response)) {
                response.forEach(exam => {
                    const startDate = new Date(exam.startDate);
                    const endDate = new Date(exam.endDate);
                    const now = new Date();
                    let status = "Upcoming";
                    let btnLabel = "View Details";

                    if (now > endDate) {
                        status = "Completed";
                        btnLabel = "View Results";
                        completedCount++;
                        if (exam.score) {
                            totalScore += exam.score;
                            scoreCount++;
                        }
                    } else if (now >= startDate && now <= endDate) {
                        status = "Ongoing";
                        btnLabel = "Take Exam";
                    } else {
                        status = "Upcoming";
                        btnLabel = "View Details";
                        upcomingCount++;

                        // Find closest upcoming exam for description only
                        if (!nextExamDate || startDate < nextExamDate) {
                            nextExamDate = startDate;
                            nextExamDesc = exam.description || "No description available";
                        }
                    }

                    // build exam card
                    cardsContainer.append(`
                            <div class="exam-card">
                                <div class="exam-card-header">
                                    <h6>${exam.description || "No description available"}</h6>
                                </div>
                                <div class="exam-card-body">
                                    <p><strong>Status:</strong> ${status}</p>
                                    <p><strong>Duration:</strong> ${exam.duration} minutes</p>
                                    <p><strong>Start:</strong> ${startDate.toLocaleDateString()}</p>
                                    <p><strong>End:</strong> ${endDate.toLocaleDateString()}</p>
                                </div>
                                <div class="exam-card-footer">
                                    <button class="btn">${btnLabel}</button>
                                </div>
                            </div>
                        `);
                });

                // Update stats
                $("#upcomingExams").text(`${upcomingCount} Upcoming Exams`);
                $("#completedExams").text(`${completedCount} Completed Exams`);
                $("#averageScore").text(`Average Score: ${scoreCount > 0 ? (totalScore / scoreCount).toFixed(1) : 0}%`);
                $("#nextExamDesc").text(nextExamDesc); // displayed in single line with styling
            }
        },
        error: function() {
            $("#examCardsContainer").html(`
                    <div class="col-12 text-center py-5">
                        <i class="fas fa-exclamation-triangle fa-4x mb-3" style="color: #dc3545;"></i>
                        <h3 class="text-danger">Error Loading Exams</h3>
                        <p>Unable to connect to the server. Please try again later.</p>
                    </div>
                `);
        }
    });
}
