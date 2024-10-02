
var goodsMap={}
var category_arr = [];
$(document).ready(function() {

    queryProduct();

})



function queryProduct() {
    const queryName = window.location.search;
    const urlParams = new URLSearchParams(queryName);
    const name = urlParams.get('name');
    const url = `/product/list?name=${encodeURIComponent(name)}`;
    $.ajax({
        type: "GET",
        url: url/* "/product/list"*/,
        /*        data: {
                    name: name // 查询参数
                },*/
        dataType: "json",
        success: function (res) {
            if (res.code == '500') {
                alert("查询失败");
                window.location.href = '/index.html'
            }
            if (res.code == '200') {
                goodsMap[name] = res.data;
                console.log(goodsMap);
                /*listSearchGoods(name);*/



                let s = "";
                let goodsArr = goodsMap[name];
                console.log(goodsArr);

                s +=
                    `<div>
            <ul>`

                let count = 0;
                for (let good of goodsArr) {
                    if (count == 0) {
                        s += `<li class="glifirst">`
                    } else {
                        s += `<li class="gli">`
                    }
                    count++;
                    if (count == 5) {
                        count = 0;
                    }

                    s +=
                        `<div class="glid1">
                <div class="glid2">
                    <a href="../goods.html?id=` + good.id + `" target="_blank">
                    
                      <img src="` + good.img + `">
                    </a>
                </div>
                <a href="../goods.html?id=` + good.id + `" target="_blank" class="ga">
                    <div>
                        <div>
                            <p class="gtitle">` + good.title + `</p>
                        </div>
                        <div>
                            <p class="gdetail">` + good.description + `</p>
                        </div>
                    </div>
                </a>
                <div>
                    <p class="gmoney">
                        <i>￥</i>` + good.price + `
                    </p>
                    
                </div>
            </div>
        </li>`
                }

                s +=
                    `<li class="cls"></li>
        </ul>
    </div>`


                $(".cc").append(s);
            }
            }
        ,}
    )
}



