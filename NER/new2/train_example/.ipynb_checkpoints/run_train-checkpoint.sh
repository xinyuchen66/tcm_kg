PRETRAINED_LM_DIR="../../../roberta_chinese"
DATA_DIR="../../../newNERquestion"
OUTPUT_DIR="../models"

python run_train.py \
    -train_data=${DATA_DIR}/data.txt \
    -dev_data=${DATA_DIR}/data.txt \
    -save_path=${OUTPUT_DIR} \
    -bert_config=${PRETRAINED_LM_DIR}/bert_config.json \
    -bert_checkpoint=${PRETRAINED_LM_DIR}/bert_model.ckpt \
    -bert_vocab=${PRETRAINED_LM_DIR}/vocab.txt \
    -device_map="0" \
    -best_fit \
    -max_epochs=3 \
    -early_stop_patience=5 \
    -reduce_lr_patience=3 \
    -reduce_lr_factor=0.5 \
    -batch_size=64 \
    -max_len=25 \
    -learning_rate=5e-6 \
    -model_type="cnn" \
    -cell_type="idcnn" \
    -cnn_filters=128 \
    -cnn_kernel_size=3 \
    -cnn_blocks=4 \
    -dropout_rate=0.1 \
    -learning_rate=5e-5 \
