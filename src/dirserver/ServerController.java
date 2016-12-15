package dirserver;

/**
 * Created by MarceloCortesao on 15/12/16.
 */
class ServerController {
    DirectoryServer Serv;

    ServerController(DirectoryServer x){Serv=x;}

    public String getListServ(){
        int x=0;
        StringBuilder List = new StringBuilder();
        for(int i = 0;i<Serv.getServerRegistries().size();i++) {
            List.append(Serv.getServerRegistries().get(i).getName()+"\n");
        }
        if (List==null)
            return "No Server's Connected";
        return List.toString();
    }

    public String getListClients(){
        int x=0;
        StringBuilder List = new StringBuilder();
        for(int i = 0;i<Serv.getCliRegistries().size();i++) {
            List.append(Serv.getCliRegistries().get(i).getName()+"\n");
        }
        if (List==null)
            return "No Server's Connected";
        return List.toString();
    }


    



}
