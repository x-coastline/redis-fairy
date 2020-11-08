import React, { useCallback } from 'react';
import { Dialog } from '@alifd/next';

const getDialogTitle = (operationType) => {
  return `${operationType} Node`
};


const DialogOperation = props => {
  const { nodeOperationType, record , ...lastProps} = props;
  let node = '';
  if (record) {
    node = `${record.host}:${record.port}`;
  }

  return (
    <Dialog
      // visible
      shouldUpdatePosition
      isFullScreen
      title={getDialogTitle(nodeOperationType)}
      style={{
        width: 600,
      }}
      {...lastProps}
    >
      {node}
      {/* <Operation ref={operationRef} actionType={actionType} dataSource={dataSource} /> */}
    </Dialog>
  );
};

export default DialogOperation;