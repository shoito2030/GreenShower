/**
 * 郵便番号検索機能用
 */

const url = 'https://zipcloud.ibsnet.co.jp/api/search?zipcode=';
const zipcode = document.getElementById("zipcode");
const loc = document.getElementById("loc");

$('#zip-btn').on('click', function (e) {
  e.
  // Ajax通信を開始
  $.ajax({
    url: url + zipcode.value,
    type: 'GET',
    dataType: 'json',
  })
    .done(function (data) {
      // 通信成功時の処理を記述
      const results = data.results[0];
      setAddress(results);


    })
    .fail(function () {
      // 通信失敗時の処理を記述
      alert("ajax失敗");
    });

    return false;
})

function setAddress(results) {
  const address1 = results.address1;
  const address2 = results.address2;
  const address3 = results.address3;

  const address = address1 + address2 + address3;

  loc.value = address;
}