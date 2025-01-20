
namespace java com.didiglobal.thriftmock.test.source


struct Request{
    1:optional string msg,
}

struct Response{
    1:required i32 code,
    2:required string responseMsg,
}


service HelloService {
    Response sayHello(1:required Request request);
    bool healthCheck();
    oneway void notifySubscriber(1: Request request);
}
service ByeService {
    Response sayBye(1:required Request request);
}
