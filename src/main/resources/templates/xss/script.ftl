这个页面存在script的xss漏洞
<script>
    var x = "${userName}"
    //....其它代码
</script>