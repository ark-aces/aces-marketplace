import { Service } from './service';

export const SERVICES: Service[] = [
  { id: 1, name: "btc.arkaces.com/btc-ark-transfer-channel", description: "A BTC to ARK Channel Service"},
  { id: 2, name: "ark.arkaces.com/ark-btc-transfer-channel", description: "An ARK to BTC Channel Service"},
  { id: 3, name: "eth.arkaces.com/eth-ark-transfer-channel", description: "An ETH to ARK Channel Service"},
  { id: 4, name: "eth.arkaces.com/eth-ark-contract-deployment", description: "An ARK to ETH Contract Deployment Service"},
  { id: 5, name: "ark.arkaces.com/ark-eth-transfer-channel", description: "An ARK to ETH Transfer Channel Service"}
];
