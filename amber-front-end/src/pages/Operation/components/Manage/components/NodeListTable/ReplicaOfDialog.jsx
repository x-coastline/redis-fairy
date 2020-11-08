import React, { useCallback } from 'react';
import { Dialog } from '@alifd/next';

const getDialogTitle = (metaType,record) => {
  switch (metaType) {
    case 'Info':
    default:
      return 'Info';
    case 'Config':
      return 'Config';
  }
};

const DialogOperation = props => {
  const { metaType, record , ...lastProps} = props;

  return (
    <Dialog
      // visible
      shouldUpdatePosition
      isFullScreen
      title='ReplicaOf'
      style={{
        width: 600,
      }}
      footer={false}
      {...lastProps}
    >
      ReplicaOf
      {/* <Operation ref={operationRef} actionType={actionType} dataSource={dataSource} /> */}
    </Dialog>
  );
};

export default DialogOperation;