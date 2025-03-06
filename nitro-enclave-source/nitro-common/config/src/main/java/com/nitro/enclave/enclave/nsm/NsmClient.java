package com.nitro.enclave.enclave.nsm;

import com.nitro.enclave.enclave.nsm.output.DescribeNsm;
import com.nitro.enclave.enclave.nsm.output.DescribePcr;

public interface NsmClient {

	DescribeNsm describeNsm();

	DescribePcr describePcr(int index);

}
