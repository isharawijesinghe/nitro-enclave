package com.nitro.enclave.host.network;

import com.nitro.enclave.network.SocketTLV;

public abstract class AbstractSocketHostClient implements HostClient {

	protected Integer port;

	protected SocketTLV socketTLV;

	public AbstractSocketHostClient(Integer port, SocketTLV socketTLV) {
		this.port = port;
		this.socketTLV = socketTLV;
	}

}
