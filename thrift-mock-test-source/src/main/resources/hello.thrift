
namespace java com.xiaoju.ddpay.thriftmock.server


struct Request{
    1:optional string msg,
}

struct Response{
    1:required i32 code,
    2:required string responseMsg,
}


service HelloService {
    Response sayHello(1:required Request request);
}
service ByeService {
    Response sayBye(1:required Request request);
}
