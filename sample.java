public class AccountHoldUploadRow {

private Long  id;

private Long uploadId;

private Long acctId;

private String vndrACCTId;

private String vndrAcctTyId;

private String validInd;

private String invalidReason;

}

******************AccountHoldServiceImpl
// TODO return AccountHoIds with valid/ invalid info
List<AccountHoldUploadRow> uploadDataList =  new ArrayList<> ( ) ;
for (AccountHoldUploadRowEntity uploaddataEntity : uploaddataEntityList) {
uploadDataList.add(AccountHoldMapper.mapToUploadData(uploaddataEntity));
}

****************AccountHoldMapper

public static AccountHoldUploadRow mapToUploadData(AccountHoldUploadRowEntity dataEntity){
return AccountHoldUploadRow.builder()
.id(dataEntity.getId())
.uploadId(dataEntity.getUploadId())
.acctId(dataEntity.getAcctId())
.vndrACCTId(dataEntity.getVndrACCTId())
.vndrAcctTyId(dataEntity.getVndrAcctTyId())
.validInd(dataEntity.getValidInd())
.invalidReason(dataEntity.getInvalidReason())
.build();
}
