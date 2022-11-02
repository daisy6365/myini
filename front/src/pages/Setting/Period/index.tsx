import { useState, useCallback } from 'react';
import './style.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPen, faCheck } from '@fortawesome/free-solid-svg-icons';

export function ProjectPeriod(props: any) {
  const [isEdit, setIsEdit] = useState(false);
  const { period } = props;
  const onSubmitClick = useCallback(() => {
    setIsEdit(false);
  }, []);
  return (
    <div className="project-period">
      <div className="project-detail-title-wrapper">
        <div className="project-detail-info-title">프로젝트 기간</div>
        {isEdit ? (
          <FontAwesomeIcon
            icon={faCheck}
            className="project-edit-button"
            onClick={onSubmitClick}
          />
        ) : (
          <FontAwesomeIcon
            icon={faPen}
            className="project-edit-button"
            onClick={() => setIsEdit(true)}
          />
        )}
      </div>
      <div className="detail-word">{period}</div>
    </div>
  );
}
