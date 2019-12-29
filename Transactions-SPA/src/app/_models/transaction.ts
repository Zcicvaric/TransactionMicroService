export class Transaction {
	id: number;
	uid: string;
	number: number;
	date: string;
	description: string;
	statusId: number;
	paymentTypeId: number;
	paymentInstrumentId: number;
	transactionAmount: number;
	payerIBAN: string;
	payerCurrency: string;
	receiverIBAN: string;
	receiverCurrency: string;
	receiverExchangeRate: number;
	swiftCode: string;
	modelId: number;
	referenceNumber: string;
	usageCode: string;
	stornoID: number;
}
