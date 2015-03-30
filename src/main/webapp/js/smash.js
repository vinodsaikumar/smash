/**
 * Custom JS for smash
 */
var _itemsPerPage = 12;
var _currentPageNo = 0;
var _pageItemsMarkup = [];
var _league;
$(document).ready(function() {
	displayLeagueSection();
	contactUsFormValidate();
	formSubmit();
	updateScoreBoardWithNewSet();
	updateScoreBoardWithOldSet();
});
function displayLeagueSection() {
	$.ajax({
		url : "league",
		type : "GET",
		dataType : 'json',
		contentType : 'application/json',
		mimeType : 'application/json',
		success : function(league) {
			_league = league;
			updateLeagueMetadata(league);
			updateRankBoard(league);
			splitScoreBoardItemsToPages(league);
			updateScoreBoard(league, _currentPageNo);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			window.location = '/error.html';
		}
	});
}
function updateScoreBoardWithNewSet() {
	$('#sb-pagination-newer').on("click", function() {
		var max = _pageItemsMarkup.length - 1;
		_currentPageNo = +_currentPageNo + 1;
		if (_currentPageNo >= max) {
			_currentPageNo = max;
		}
		updateScoreBoard(_league, _currentPageNo);
	});
}
function updateScoreBoardWithOldSet() {
	$('#sb-pagination-older').on("click", function() {
		var min = 0;
		_currentPageNo = +_currentPageNo - 1;
		if (_currentPageNo <= min) {
			_currentPageNo = min;
		}
		updateScoreBoard(_league, _currentPageNo);
	
	});
}
function updateLeagueMetadata(league) {
	$('#league-metadata-venue').append(league.venue + " ");
	$('#league-metadata-time').append(league.time + " ");
	$('#league-metadata-duration').append(
			league.startDate + " to " + league.endDate + " ");
}
function updateRankBoard(league) {
	$.each(league.teamPerformances, function(index, team) {
		var $rank = index + 1;
		var $teamPerformanceRow = $('<tr>').append($('<td>').text($rank),
				$('<td>').text(team.playersName),
				$('<td>').text(team.matchesPlayed),
				$('<td>').text(team.matchesWon),
				$('<td>').text(team.totalSetsPlayedToWin),
				$('<td>').text(team.wonByPoints),
				$('<td>').text(team.avgSetPoints));
		$('#team-performance').append($teamPerformanceRow);
	});
}
function updateScoreBoard(league, pageNo) {
	$('#score-board').empty();
	var pageMarkup = _pageItemsMarkup[+pageNo];
	var pageMarkupTokens = pageMarkup.split("-");
	var start = pageMarkupTokens[0];
	var end = pageMarkupTokens[1];
	$.each(league.scoreBoard.slice(start, end),
			function(index, scoreBoardItem) {
				var $scoreBoardRow = $('<tr>').append(
						$('<td>').text(scoreBoardItem.matchDate),
						$('<td>').text(scoreBoardItem.teamOnePlayers),
						$('<td>').text(scoreBoardItem.teamTwoPlayers),
						$('<td>').text(scoreBoardItem.scores));
				$('#score-board').append($scoreBoardRow);
			});
}

function splitScoreBoardItemsToPages(league) {
	var totalItems = league.scoreBoard.length;
	var start = 0;
	var end = _itemsPerPage;
	var markupItem = start + "-" + end;
	_pageItemsMarkup.push(markupItem);
	while (end < totalItems) {
		start = end;
		end = +end + _itemsPerPage;
		markupItem = start + "-" + end;
		_pageItemsMarkup.push(markupItem);
	}
}
function formSubmit() {
	$("#contactus-form").submit(
			function(e) {
				$("#contact-us-form-submit-button").button('loading');
				$("#contactus-fields-serverside-error-message").empty();
				$("#contactus-fields-serverside-success-message").empty();
				var jsonPostData = ConvertFormToJSON(this);
				var formURL = "contactus";
				$.ajax({
					url : formURL,
					type : "POST",
					data : jsonPostData,
					dataType : "json",
					success : function(contactUsResponse, textStatus, jqXHR) {
						if (contactUsResponse.robot) {
							$("#contactus-fields-captcha-error").text(
									"Are you a robot?");
						} else if (!contactUsResponse.captchaValid) {
							$("#contactus-fields-captcha-error").text(
									"Verification failed. Are you a robot?");
						} else if (!contactUsResponse.submited) {
							$("#contactus-fields-serverside-error-message")
									.text(contactUsResponse.message);
						} else {
							$("#contactus-fields-serverside-success-message")
									.text(contactUsResponse.message);
							$(".contactus-fields-error").children().eq(0)
									.empty();
						}
						$("#contact-us-form-submit-button").button('reset');
					},
					error : function(jqXHR, textStatus, errorThrown) {
						window.location = '/error.html';
					}
				});
				e.preventDefault();
				e.unbind();
			});
}
function ConvertFormToJSON(form) {
	var array = jQuery(form).serializeArray();
	var json = {};

	jQuery.each(array, function() {
		json[this.name] = this.value || '';
	});

	return json;
}
function contactUsFormValidate() {
	$("#contactus-form").validate({
		rules : {
			fname : {
				required : true,
				maxlength : 50
			},
			lname : {
				required : true,
				maxlength : 50
			},
			email : {
				required : true,
				email : true,
				maxlength : 50
			},
			message : {
				required : true,
				maxlength : 500
			},

		},
		messages : {
			fname : {
				required : "Please enter your first name",
				maxlength : "Please enter no more than 50 characters"
			},
			lname : {
				required : "Please enter your last name",
				maxlength : "Please enter no more than 50 characters"
			},
			email : {
				required : "Please enter your email id",
				email : "Please enter a valid email address",
				maxlength : "Please enter no more than 50 characters"
			},
			message : {
				required : "Please enter a message",
				maxlength : "Please enter no more than 500 characters"
			},

		},
		errorPlacement : function(error, element) {
			error.appendTo(element.parent().parent().next().children().eq(0));
		}

	});

}