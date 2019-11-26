package die.mass.services;

import die.mass.protocol.Protocol;
import die.mass.servers.GetTokenServiceImpl;

public class AdminService {

    private GetTokenServiceImpl getTokenService;

    public AdminService(GetTokenServiceImpl getTokenService) {
        this.getTokenService = getTokenService;
    }

    public boolean isAdmin(Protocol protocolIn) {
        return getTokenService.getData(protocolIn.getPayload().getToken(), "role").equals("1");
    }
}
