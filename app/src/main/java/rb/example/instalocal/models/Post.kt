package rb.example.instalocal.models

data class Post(
    var description:String =" ",
    var image_url:String =" ",
    var creation_time_ms:Long =0,
    var user: User?=null
    //this names should be exactly same as that in Firebase Storage
)