package com.videumcorp.gitlab.classes.gson.gitlabuser;

import com.google.gson.annotations.SerializedName;

class IdentitiesItem {

    @SerializedName("provider")
    private String provider;

    @SerializedName("extern_uid")
    private String externUid;

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public void setExternUid(String externUid) {
        this.externUid = externUid;
    }

    public String getExternUid() {
        return externUid;
    }

    @Override
    public String toString() {
        return "IdentitiesItem{" + "provider = '" + provider + '\'' + ",extern_uid = '" +
                externUid + '\'' + "}";
    }
}